$(document).ready(function() {
    $('.menu').click(function() {
        $('nav').slideToggle();
        $(".bar").toggleClass('change');
    });

    function showSection(targetId) {
        $('.section').each(function() {
            if (this.id === targetId) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    }

    var activeSection = $('input[name="activeSection"]').val() || 'QR-Code';
    showSection(activeSection)

    $('.nav-link').click(function(e) {
        e.preventDefault();
        var targetId = $(this).data('target');
        showSection(targetId);
    });

    $('#comprar-tickets').click(function() {
        showSection('comprar');
    });

    $('#logout-btn').click(function() {
        window.location.href = 'login';
    });
});

const fileInput = document.getElementById('file');
const fileNameSpan = document.getElementById('file-name');

fileInput.addEventListener('change', function () {
    if (fileInput.files.length > 0) {
        fileNameSpan.textContent = fileInput.files[0].name;
    } else {
        fileNameSpan.textContent = 'Nenhum arquivo selecionado';
    }
});

function showSection(targetId) {
    $('.section').hide();
    $('#' + targetId).show();
}

function setActiveLink(linkElement) {
    const isDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    $('.nav-link').removeClass('active');
    $(linkElement).addClass('active');

    if(!isDarkMode) {
        $('#comprar-icon').attr('src', '/assets/carrinho.png');
        $('#usuario-icon').attr('src', '/assets/user.png');
        $('#carteira-icon').attr('src', '/assets/carteira.png');
        $('#qrcode-icon').attr('src', '/assets/scanner.png');

        if ($(linkElement).data('target') === 'comprar') {
            $('#comprar-icon').attr('src', '/assets/carrinhobranco.png');
        } else if ($(linkElement).data('target') === 'carteira') {
            $('#carteira-icon').attr('src', '/assets/carteirabranco.png');
        } else if ($(linkElement).data('target') === 'usuario') {
            $('#usuario-icon').attr('src', '/assets/userbranco.png');
        } else if ($(linkElement).data('target') === 'QR-Code') {
            $('#qrcode-icon').attr('src', '/assets/scannerbranco.png');
        }
    }
}


function toggleIconsForDarkMode(isDarkMode) {
    const comprarIcon = $('#comprar-icon');
    const carteiraIcon = $('#carteira-icon');
    const usuarioIcon = $('#usuario-icon');
    const qrcodeIcon = $('#qrcode-icon');

    if (isDarkMode) {
        comprarIcon.attr('src', '/assets/carrinhobranco.png');
        carteiraIcon.attr('src', '/assets/carteirabranco.png');
        usuarioIcon.attr('src', '/assets/userbranco.png');
        qrcodeIcon.attr('src', '/assets/scannerbranco.png');
    } else {
        comprarIcon.attr('src', '/assets/carrinho.png');
        carteiraIcon.attr('src', '/assets/carteira.png');
        usuarioIcon.attr('src', '/assets/user.png');
        qrcodeIcon.attr('src', '/assets/scanner.png');
    }
}
function checkDarkMode() {
    const isDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    toggleIconsForDarkMode(isDarkMode);
}

window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', event => {
    checkDarkMode();
});

$(document).ready(function() {
    checkDarkMode();
});


document.addEventListener('DOMContentLoaded', function () {
    function applyTheme(theme) {
        const activeLinks = document.querySelectorAll('.nav-link.active');

        activeLinks.forEach(link => {
            if (theme === 'dark') {
                link.classList.add('dark-mode');
            } else {
                link.classList.remove('dark-mode');
            }
        });
    }

    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
        applyTheme('dark');
    }

    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', event => {
        const newColorScheme = event.matches ? 'dark' : 'light';
        applyTheme(newColorScheme);
    });
});