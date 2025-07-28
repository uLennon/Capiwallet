document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('formRedefinirSenha');
    const senha = document.getElementById('NovaSenha');
    const confirmaSenha = document.getElementById('ConfirmaSenha');
    const mensagemErro = document.getElementById('mensagemErro');
    const submitButton = document.getElementById('submitButton');

    function validarSenhas() {
        if (senha.value !== confirmaSenha.value) {
            mensagemErro.style.display = 'block';
            submitButton.disabled = true;
        } else {
            mensagemErro.style.display = 'none';
            submitButton.disabled = false;
        }
    }

    senha.addEventListener('input', validarSenhas);
    confirmaSenha.addEventListener('input', validarSenhas);

    form.addEventListener('submit', function (event) {
        if (senha.value !== confirmaSenha.value) {
            event.preventDefault();
        }
    });
});
