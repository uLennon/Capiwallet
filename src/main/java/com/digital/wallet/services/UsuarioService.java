package com.digital.wallet.services;

import com.digital.wallet.auxiliar.PasswordResetToken;
import com.digital.wallet.config.AppConfig;
import com.digital.wallet.model.Carteira;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.PasswordResetTokenRepository;
import com.digital.wallet.repositories.UsuarioRepository;
import com.digital.wallet.util.Identificador;
import com.google.zxing.WriterException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CarteiraService carteiraService;
    private final QRCodeService qrCodeService;
    private final ExecutorService executorService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;
    private final AppConfig appConfig;

    public UsuarioService(UsuarioRepository usuarioRepository, CarteiraService carteiraService,QRCodeService qrCodeService,ExecutorService executorService, EmailService emailService, PasswordResetTokenRepository tokenRepository, AppConfig appConfig) {
        this.usuarioRepository = usuarioRepository;
        this.carteiraService = carteiraService;
        this.qrCodeService = qrCodeService;
        this.executorService = executorService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
        this.appConfig = appConfig;
    }

    public void criarUsuario(Usuario usuario) {
        usuario.setTipoUsuario(Identificador.INDEFINIDO);
        try {
            byte[] image = getImageAsBytes("static/assets/padrao.png");
            usuario.setDataImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            byte[] qrCodeImage = qrCodeService.gerarImagemQrCode(usuario.getMatricula());
            usuario.setQrCodeImage(qrCodeImage);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
        usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorMatricula(String matricula) {
        return usuarioRepository.findAllByMatricula(matricula);
    }

    public List<Usuario> buscarUsuariosPorTipo(Identificador tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario);
    }

    public void aceitarUsuario(String matricula) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByMatricula(matricula);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            Carteira carteira = new Carteira();
            carteiraService.salvarCarteira(carteira);
            usuario.setCarteira(carteira);

            usuario.setTipoUsuario(Identificador.USUARIO);

            usuarioRepository.save(usuario);

            executorService.submit(()->{
                emailService.sendEmail(usuario.getEmail(),"Cadastro aceito","Olá, " + usuario.getNome() + "! Estamos felizes em informar que sua carteira digital para o bandejão foi ativada. Agora, você já pode começar a comprar seus tickets e utilizá-los no bandejão!");
            });
        } else {
            throw new RuntimeException("Usuário com matrícula " + matricula + " não encontrado");
        }
    }

    public void rejeitarUsuario(String matricula) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByMatricula(matricula);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            try {
                usuarioRepository.delete(usuario);
                System.out.println("Usuário com matrícula " + matricula + " excluído com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao excluir usuário com matrícula " + matricula + ": " + e.getMessage());
                throw new RuntimeException("Erro ao excluir usuário com matrícula " + matricula, e);
            }
        } else {
            throw new RuntimeException("Usuário com matrícula " + matricula + " não encontrado");
        }
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    private byte[] getImageAsBytes(String imagePath) throws IOException {
        Resource resource = new ClassPathResource(imagePath);
        return Files.readAllBytes(resource.getFile().toPath());
    }

    public boolean enviarLinkRecuperacao(String matricula, String email) {
        Usuario usuario = usuarioRepository.findAllByMatricula(matricula);
        if (usuario == null) {
            return false;
        }
        if(usuario.getEmail().equals(email)){
            String token = generateToken();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setUsuario(usuario);
            resetToken.setToken(token);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Define a expiração do token
            tokenRepository.save(resetToken);

            executorService.submit(()->{
                emailService.sendEmail(usuario.getEmail(),"Redefinir senha","Olá, " + usuario.getNome() + "! Para redefinir sua senha, por favor, clique no link abaixo:\n"
                        + appConfig.getDomain() + "/redefinir-senha?token=" + token + "\n"
                        + "Este link é válido por 1 hora. Se você não solicitou a redefinição de senha, pode ignorar esta mensagem.");
            });
            return true;
        }
        return false;
    }

    public boolean trocarSenha(String token, String novaSenha) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        Usuario user = resetToken.getUsuario();
        user.setSenha(novaSenha);
        usuarioRepository.save(user);
        tokenRepository.delete(resetToken);
        return true;
    }
    private String generateToken() {
        return UUID.randomUUID().toString();
    }


}
