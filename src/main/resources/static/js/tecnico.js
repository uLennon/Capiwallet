$(document).ready(function() {
    var $modal = $('#modal');
    var $modalMessage = $('#modal-message');
    var $modalConfirm = $('#modal-confirm');
    var $modalCancel = $('#modal-cancel');

    $('#menu-toggle').on('click', function() {
        $('#nav-menu').slideToggle();
    });

    function abrirModal(mensagem, callbackConfirm) {
        $modalMessage.text(mensagem);
        $modal.show();

        $modalConfirm.off('click').on('click', function() {
            callbackConfirm();
            fecharModal();
        });

        $modalCancel.off('click').on('click', function() {
            fecharModal();
        });
    }

    function fecharModal() {
        $modal.hide();
    }


    $('#busca').on('keyup', function() {
        var termoBusca = $(this).val().toLowerCase();
        $('.usuario-item').filter(function() {
            var nome = $(this).find('p').first().text().toLowerCase();
            var matricula = $(this).find('p:nth-child(2)').text().toLowerCase();
            $(this).toggle(nome.includes(termoBusca) || matricula.includes(termoBusca));
        });
    });

    $('#menu-usuario').on('click', function() {
        $('#nav-menu-usuario').slideToggle();
    });


    document.getElementById('openModal').onclick = function() {
        document.getElementById('modal-reembolso').style.display = 'block';
    }


    document.getElementById('closeModal').onclick = function() {
        document.getElementById('modal-reembolso').style.display = 'none';
    }

    window.onclick = function(event) {
        if (event.target === document.getElementById('modal-reembolso')) {
            document.getElementById('modal-reembolso').style.display = 'none';
        }
    }

    function onlyOne(checkbox) {
        const checkboxes = document.getElementsByName('meal');
        checkboxes.forEach((item) => {
            if (item !== checkbox) item.checked = false;
        });
    }

    function validateForm() {
        const cafe = document.getElementById('cafe').checked;
        const almoco = document.getElementById('almoco').checked;
        const textInput = document.getElementById('textInput').value;

        if (!cafe && !almoco) {
            alert("Por favor, selecione Café da Manhã ou Almoço.");
            return false;
        }
        if (textInput.trim() === "" || isNaN(textInput)) {
            alert("O campo deve conter apenas números.");
            return false;
        }
        return true;
    }
});


document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function (e) {
        e.preventDefault();

        document.getElementById('buscar-qrcode').style.display = 'none';
        document.getElementById('buscar-matricula').style.display = 'none';
        document.querySelectorAll('.usuario-item').forEach(item => {
            item.style.display = 'none'; // Esconde os resultados de usuário
        });

        const target = this.getAttribute('data-target');
        if (target === 'buscar-qrcode') {
            document.getElementById('buscar-qrcode').style.display = 'grid';
        } else if (target === 'buscar-matricula') {
            document.getElementById('buscar-matricula').style.display = 'block';
        }
    });
});


document.addEventListener('DOMContentLoaded', function() {
    var tipoBuscaElement = document.getElementById('tipoBusca');
    var tipoBusca = tipoBuscaElement.getAttribute('data-tipo-busca');

    if (tipoBusca === 'matricula') {
        document.getElementById('buscar-matricula').style.display = 'block';
        document.getElementById('buscar-qrcode').style.display = 'none';
    } else if (tipoBusca === 'qrcode') {
        document.getElementById('buscar-matricula').style.display = 'none';
        document.getElementById('buscar-qrcode').style.display = 'block';
    }
});

const video = document.getElementById('video');
const canvas = document.getElementById('canvas');
const context = canvas.getContext('2d');
const qrResult = document.getElementById('qrResult');
const qrCodeInput = document.getElementById('qrCodeInput');
const submitQR = document.getElementById('submitQR');
const startButton = document.getElementById('startButton');


startButton.addEventListener('click', async function () {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({
            video: {
                facingMode: 'environment',
                width: { ideal: 640 },
                height: { ideal: 480 }
            }
        });
        video.srcObject = stream;
        video.setAttribute('playsinline', true);
        video.play();
        video.onloadedmetadata = () => {
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            tick();
        };
    } catch (err) {
        console.error("Erro ao acessar a câmera: ", err);
    }
});

function tick() {
    if (video.readyState === video.HAVE_ENOUGH_DATA) {
        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
        const code = jsQR(imageData.data, imageData.width, imageData.height);

        if (code) {
            qrResult.textContent = `QR Code detectado: ${code.data}`;
            qrCodeInput.value = code.data;
            submitQR.click();
        }
    }

    setTimeout(() => {
        requestAnimationFrame(tick);
    }, 500);
}

document.getElementById('scan-button').addEventListener('click', () => {
    alert("Botão foi clicado");
});
