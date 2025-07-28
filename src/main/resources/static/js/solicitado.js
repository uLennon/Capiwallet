document.addEventListener("DOMContentLoaded", function() {
    function atualizaCadastro() {
        const logoImage = document.getElementById('imagemResponsiva');
        if (window.innerWidth > 600) {
            logoImage.src = './assets/capivarafalandodocadastro.png';
        } else {
            logoImage.src = './assets/capivarafalandomobile.png';
        }
    }

    atualizaCadastro();
    window.addEventListener('resize', atualizaCadastro);
});