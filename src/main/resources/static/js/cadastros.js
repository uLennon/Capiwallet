document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('modal-rejeitar');
    const closeModal = document.getElementById('close-rejeitar');
    const form = document.getElementById('form-rejeitar');

    document.querySelectorAll('.btn-rejeitar').forEach(button => {
        button.addEventListener('click', function() {
            const userId = this.dataset.id;
            document.getElementById('user-id-rejeitar').value = userId;
            modal.style.display = 'block';
        });
    });

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
        alert('Motivo da rejeição enviado!');
        modal.style.display = 'none';
    });
});
