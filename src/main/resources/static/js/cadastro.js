document.addEventListener("DOMContentLoaded", function() {
    function updateLogo() {
        const logoImage = document.getElementById('logo-image');
        const isDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
        const isMobile = window.innerWidth <= 600;

        if (isMobile) {
            if (isDarkMode) {
                logoImage.src = './assets/CAPIVARAPARALOGOESCURO.png';
            } else {
                logoImage.src = './assets/CAPIVARAPARALOGOCLARO.png';
            }
        } else {
            logoImage.src = './assets/CAPIVARAUFRRJ.png';
        }
    }
    updateLogo();
    window.addEventListener('resize', updateLogo);
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', updateLogo);
});

function openModal() {
    document.getElementById('modal-redefinir-senha').style.display = 'block';
}


function closeModal(elemento) {
    document.getElementById(elemento).style.display = 'none';
}


document.getElementById('openModalBtn').onclick = function(event) {
    event.preventDefault(); // Impede a navegação do link
    openModal();
};


window.onclick = function(event) {
    if (event.target == document.getElementById('modal-redefinir-senha')) {
        closeModal();
    }
}

function validarFormulario() {
    var email = document.getElementById('email').value;
    var matricula = document.getElementById('matricula').value;
    var senha = document.getElementById('senha').value;
    var nome = document.getElementById('nome').value;

    if (senha.length < 1) {
        alert('A senha deve conter pelo menos um caractere.');
        return false;
    }

    if (nome.length < 1) {
        alert('O nome deve conter pelo menos um caractere.');
        return false;
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Por favor, insira um e-mail válido.');
        return false;
    }

    var matriculaRegex = /^[0-9]+$/;
    if (!matriculaRegex.test(matricula)) {
        alert('A matrícula deve conter apenas números.');
        return false;
    }

    return true;
}

document.addEventListener("DOMContentLoaded", function() {
    var errorMessage = document.getElementById("error-message");

    if (errorMessage) {
        setTimeout(function() {
            errorMessage.classList.add("fade-out");
            setTimeout(function() {
                errorMessage.classList.add("hidden");
                errorMessage.classList.remove("fade-out");
            }, 500);
        }, 5000);
    }
});