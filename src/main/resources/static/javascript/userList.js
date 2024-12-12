$(document).ready(function () {
    $(document).on('click', '.delete-btn', function () {
        var userId = $(this).closest('.dropdown-menu').find('.userid').val();
        console.log("user Id" + userId);

        // Use jQuery Confirm for confirmation
        $.confirm({
            title: 'Confirm Deletion',
            type: 'orange',
            content: 'Are you sure you want to delete this user?',
            buttons: {
                confirm: function () {
                    console.log("User ID to delete:", userId);

                    // Example: Send an AJAX request to delete the user
                    $.ajax({
                        url: '/secure/deleteUser', // Replace with your endpoint
                        type: 'DELETE',
                        data: {
                            userId: userId
                        },
                        success: function (response) {
                            if (response.resCode === "Success") {
                                $.confirm({
                                    title: response.resCode,
                                    content: response.response, // Display the success message
                                    type: 'green',
                                    buttons: {
                                        ok: function () {
                                            location.reload();
                                        }
                                    }
                                });
                            } else {
                                $.confirm({
                                    title: response.resCode,
                                    content: response.response, // Display the failure message
                                    type: 'red',
                                    buttons: {
                                        ok: function () {
                                            // Optional actions for failure
                                        }
                                    }
                                });
                            }
                        },
                        error: function () {
                            $.confirm({
                                title: 'Error',
                                content: 'An unexpected error occurred. Please try again later.',
                                type: 'red',
                                buttons: {
                                    ok: function () {
                                        // Optional actions on error
                                    }
                                }
                            });
                        }

                    });
                },
                cancel: function () {
                    console.log("Delete canceled");
                }
            }
        });
    });


    $(document).on('click', '.details-btn', function () {
            var userId = $(this).closest('.dropdown-menu').find('.userid').val();
            window.location.href = '/secure/userDetails/' + userId;
        });
});
