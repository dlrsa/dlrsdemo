$(document).ready(function() {
        $('.selectpicker').selectpicker();

        $('#btnclick').click(function (e) {
                e.preventDefault();
                let formData = $('#teamDetails').serializeArray();
                console.log(formData);
                $.ajax({
                    type: 'POST',
                    url: '/secure/updateTeam',
                    data: $.param(formData), // Ensure array data is properly sent
                    success: function(response) {
                        alert(response);
                        location.reload();
                    },
                    error: function(error) {
                        console.error('Error:', error);
                    }
                });


            });
    });