$(document).ready(function() {
        $('.selectpicker').selectpicker();


        $('#btnclick').click(function (e) {
                e.preventDefault();
                let formData = $('#teamform').serializeArray();
                console.log(formData);
                $.ajax({
                    type: 'POST',
                    url: '/secure/createTeam',
                    data: $.param(formData), // Ensure array data is properly sent
                    success: function(response) {
                        alert(response);
                    },
                    error: function(error) {
                        console.error('Error:', error);
                    }
                });


            });
    });