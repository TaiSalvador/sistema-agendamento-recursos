document.querySelectorAll('.excluir').forEach(function(button) {

    button.addEventListener('click', function () {

        if (confirm('Confirma a exclusão?')) {

            const linha = this.closest('tr');
            const id = this.dataset.id;

            fetch(`/recursoexcluir/${id}`, {
                method: 'DELETE'
            })

            .then(async response => {

                const mensagem = await response.text();

                if (response.ok) {

                    alert(mensagem);

                    linha.remove();

                } else {

                    alert(mensagem);

                }

            })

            .catch(error => {

                console.error(error);

                alert("Erro de comunicação com o servidor.");

            });

        }

    });

});