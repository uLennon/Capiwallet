$('#menu-toggle').on('click', function() {
    $('#nav-menu').slideToggle();
});

function calcularTotal() {
    var precoCafeManha = 0.70;
    var precoAlmoco = 1.45;

    var qtdCafeManha = Math.max(parseInt(document.getElementById('cafeManha').value) || 0, 0);
    var qtdAlmoco = Math.max(parseInt(document.getElementById('almoco').value) || 0, 0);

    var total = (qtdCafeManha * precoCafeManha) + (qtdAlmoco * precoAlmoco);
    document.getElementById('total').textContent = 'Total: R$ ' + total.toFixed(2);

    if (qtdCafeManha > 0 || qtdAlmoco > 0) {
        document.getElementById('submitBtn').disabled = false;
    } else {
        document.getElementById('submitBtn').disabled = true;
    }
}

function calcularTroco() {
    var total = parseFloat(document.getElementById('total').textContent.replace('Total: R$ ', '')) || 0;
    var valorRecebido = parseFloat(document.getElementById('valorRecebido').value) || 0;

    var troco = valorRecebido - total;
    document.getElementById('troco').textContent = 'Troco: R$ ' + troco.toFixed(2);
}

function abrirModal(event) {
    event.preventDefault();

    var matricula = document.getElementById('matriculaUsuario').value;

    fetch(`/tecnico/comprar?matricula=${matricula}`)
        .then(response => response.json())
        .then(data => {
            console.log(data)
            document.getElementById('nomeComprador').textContent = data.nome;
            document.getElementById('fotoComprador').src = data.foto;
            document.getElementById('modalMatricula').value = matricula;
            document.getElementById('modalCafeManha').value = document.getElementById('cafeManha').value || 0;
            document.getElementById('modalAlmoco').value = document.getElementById('almoco').value || 0;

            var total = (document.getElementById('cafeManha').value * 0.70) + (document.getElementById('almoco').value * 1.45);
            document.getElementById('total').textContent = 'R$ ' + total.toFixed(2);
            document.getElementById('confirmModal').style.display = 'block';
        });

    return false;
}

function fecharModal() {
    document.getElementById('confirmModal').style.display = 'none';
}

var closeModal = document.getElementsByClassName('close')[0];
closeModal.onclick = function() {
    var modal = document.getElementById('confirmModal');
    modal.style.display = 'none';
};

document.getElementById('confirmBtn').onclick = function() {
    document.getElementById('ticket-form').submit();
};

window.onclick = function(event) {
    var modal = document.getElementById('confirmModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
};

calcularTotal();

document.addEventListener('DOMContentLoaded', function() {
    function showSection(sectionId) {
        const sections = document.querySelectorAll('.section');
        sections.forEach(section => {
            section.classList.add('hidden');
        });
        document.getElementById(sectionId).classList.remove('hidden');
    }

    showSection('comprar');

    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            const target = event.target.getAttribute('data-target');
            showSection(target);
        });
    });
});