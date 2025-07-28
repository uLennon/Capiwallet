package com.digital.wallet.api.service;


import com.digital.wallet.api.model.*;
import com.digital.wallet.model.Compra;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.CompraRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class PagamentoService {

    @Value("${config.clientId}")
    private String clientId;

    @Value("${config.clientSecret}")
    private String clientSecret;

    @Value("${config.urlAuth}")
    private String urlAuth;

    @Value("${config.urlApi}")
    private String urlApi;

    @Value("${config.chave}")
    private String chave;

    @Value("${config.chavePagamento}")
    private String chavePagamento;

    @Value("${config.urlPagamento}")
    private String urlPagamento;

    @Value("${config.chavePix}")
    private String chavePix;


    private final CompraRepository compraRepository;
    private final RestTemplate restTemplate;

    public PagamentoService(CompraRepository compraRepository, RestTemplate restTemplate) {
        this.compraRepository = compraRepository;
        this.restTemplate = restTemplate;
    }

    public Token pegarToken() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        return  restTemplate.exchange(urlAuth, HttpMethod.POST, entity, Token.class).getBody();
    }

    public PixGerado criarPix(Token token, PixModel pix) throws JsonProcessingException {
        String baseUrl = urlApi+"/pix/v2/cob";
        HttpHeaders headers = getHttpHeaders(token);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<PixGerado> response = restTemplate.exchange(baseUrl+"?gw-dev-app-key="+chave , HttpMethod.POST,new HttpEntity<>(objectMapper.writeValueAsString(pix),headers), PixGerado.class);

        return response.getBody();
    }

    public void pagarPix(String pixCopiaECola){
        String requestBody = "{\"pix\": \""+pixCopiaECola+"\"}";
        restTemplate.exchange(urlPagamento + "?gw-app-key=" + chavePagamento, HttpMethod.POST, new HttpEntity<>(requestBody), String.class);
    }

    public String consultarPixPago(String txid, Token token){
        HttpHeaders headers = getHttpHeaders(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<PixGerado> response = restTemplate.exchange(urlApi+"/pix/v2/cob/{txid}?gw-dev-app-key="+chave, HttpMethod.GET,request, PixGerado.class,txid);

        return response.getBody().getStatus();
    }

    private HttpHeaders getHttpHeaders(Token token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "Bearer "+ token.getAccess_token();
        headers.set("Authorization", auth);
        return headers;
    }

    public PixGerado armazenarCompra(Integer quantidadeTicketCafeManha, Integer quantidadeTicketRefeicao, Usuario usuario) throws Exception {
        Calendario calendario = new Calendario();
        calendario.setExpiracao(3600);
        Valor valor = new Valor();
        valor.calculaValor(quantidadeTicketCafeManha, quantidadeTicketRefeicao);
        PixModel pixModel = new PixModel();
        pixModel.setCalendario(calendario);
        pixModel.setValor(valor);
        pixModel.setChave(chavePix);
        Token token = pegarToken();
        PixGerado pixGerado = criarPix(token, pixModel);
        Compra compra = criarCompra(usuario, pixGerado, quantidadeTicketCafeManha,quantidadeTicketRefeicao);
        compraRepository.save(compra);
        return pixGerado;
    }

    private Compra criarCompra(Usuario usuario, PixGerado pixGerado, Integer cafeManha, Integer refeicao) {
        Compra compra = Compra.builder()
                        .usuario(usuario)
                        .valor(pixGerado.getValor().getOriginal())
                        .txid(pixGerado.getTxid())
                        .status(pixGerado.getStatus())
                        .pixCopiaECola(pixGerado.getPixCopiaECola())
                        .quantidadeTicketRefeicao(refeicao)
                        .quantidadeTicketCafeManha(cafeManha)
                        .build();
        compra.setData();
        return compra;
    }
}
