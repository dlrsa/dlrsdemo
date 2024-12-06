$(document).ready(function() {
//alert("hello")
    $('#btnclick').click(function(e) {
            e.preventDefault();
            const loginCredentials = {
                email: $('#email').val(),
                password: $('#password').val()
            };

            console.log(loginCredentials);

            const loginCredentialsJson = JSON.stringify(loginCredentials);
            console.log("User loginCredentials JSON", loginCredentialsJson);
//            alert(loginCredentialsJson);


            $.ajax({
                url: '/userlogin',
                type: 'POST',
                contentType: 'application/json',
                data: loginCredentialsJson,
                success: function (data) {
                    console.log("User Data Details - ", data)
//                    alert(data.name)
                    var requestUrl = "/secure/home";
                    window.location.href = requestUrl;

                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 400) {
                        $.alert({
                            title: 'Error!',
                            content: "Username or password is incorrect",
//                            content: jqXHR.responseText,
                            type: 'red'
                        });
                    }
                    else if (jqXHR.status === 409) {
                        $.alert({
                            title: 'Password Expired!',
                            content: "Your password is expired. Please reset the password.",
                        //  content: jqXHR.responseText,
                            type: 'orange', // Warning style
                            buttons: {
                                ok: {
                                    text: 'Reset Password',
                                    action: function () {
                                        // Redirect to a new page after OK is clicked
                                        window.location.href = "/public/resetexpiredpassword"; // Change to your desired URL
                                    }
                                }
                            }
                        });
                    }
                     else {
                        $.alert({
                            title: 'Error!',
                            content: 'An error occurred: ' + textStatus,
                            type: 'red'
                        });
                    }
                }
            });
        });
});
