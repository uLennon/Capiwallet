document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('modal-editar');
    const closeModal = document.getElementById('close-editar');
    const form = document.getElementById('form-editar-usuario');

    closeModal.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    window.addEventListener('click', function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });

    form.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Alterações salvas!');
        modal.style.display = 'none';
    });
});
