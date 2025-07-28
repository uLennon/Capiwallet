document.addEventListener('DOMContentLoaded', function() {
    const modalRejeitar = document.getElementById('modal-rejeitar');
    const closeModalRejeitar = document.getElementById('close-rejeitar');
    const formRejeitar = document.getElementById('form-rejeitar');

    const modalEditar = document.getElementById('modal-editar');
    const closeModalEditar = document.getElementById('close-editar');
    const formEditar = document.getElementById('form-editar-usuario');


    function abrirModalRejeitar(userId) {
        document.getElementById('user-id-rejeitar').value = userId;
        modalRejeitar.style.display = 'block';
    }

    function showSection(targetId) {
        $('.section').each(function() {
            if (this.id === targetId) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    }

    var activeSection = $('input[name="activeSection"]').val() || 'cadastroAdm';
    showSection(activeSection);

    function abrirModalEditar(userData) {
        $('#user-id-editar').val(userData.matricula);
        $('#novo-nome').val(userData.nome);
        $('#novo-email').val(userData.email);
        $('#nova-senha').val(userData.senha);
        $('#aceitar').prop('checked', userData.tipoUsuario === 'TECNICO');
        $('#modal-editar').show();
    }
    closeModalRejeitar.addEventListener('click', function() {
        modalRejeitar.style.display = 'none';
    });

    closeModalEditar.addEventListener('click', function() {
        modalEditar.style.display = 'none';
    });

    window.addEventListener('click', function(event) {
        if (event.target === modalRejeitar) {
            modalRejeitar.style.display = 'none';
        } else if (event.target === modalEditar) {
            modalEditar.style.display = 'none';
        }
    });

    formRejeitar.addEventListener('submit', function(e) {
        e.preventDefault();

        const formData = new FormData(formRejeitar);

        fetch(formRejeitar.action, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    modalRejeitar.style.display = 'none';
                    window.location.reload();
                } else {
                    alert('Erro ao enviar motivo da rejeição.');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao enviar motivo da rejeição.');
            });
    });

    $('#form-editar-usuario').on('submit', function(e) {
        e.preventDefault();

        const formData = new FormData(this);

        fetch($(this).attr('action'), {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    alert('Usuário atualizado com sucesso!');
                    $('#modal-editar').hide();
                    window.location.reload();
                } else {
                    alert('Erro ao atualizar usuário.');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao atualizar usuário.');
                console.log('nao ta funcinando mesmo')
            });
    });

    $(document).ready(function() {
        $('.menu').click(function() {
            $('nav').slideToggle();
        });
        function mostrarSeccao(target) {
            $('#content > div').hide();
            $('#' + target).show();
        }

        $('.nav-link').click(function(e) {
            e.preventDefault();
            var target = $(this).data('target');
            mostrarSeccao(target);
        });

        $('#content').on('click', '.btn-rejeitar', function() {
            var userId = $(this).closest('.usuario-item').find('input[name="matricula"]').val();
            abrirModalRejeitar(userId);
        });

        $('#alterarUsuario').on('click', '.btn-editar', function() {
            var userData = {
                nome: $(this).data('nome'),
                email: $(this).data('email'),
                matricula: $(this).data('matricula'),
                senha: $(this).data('senha'),
                tipoUsuario: $(this).data('tipo')
            };

            abrirModalEditar(userData);
        });

    });
});
