    $(document).ready(function () {
        $('#btnclick').on('click', function (event) {
            event.preventDefault(); // Prevent default form submission

            // Retrieve field values
            const email = $('#email').val().trim();
            const password = $('#password').val().trim();

            // Check if fields are empty
            if (!email || !password) {
                // Show confirmation dialog using jQuery Confirm
                $.confirm({
                    title: '',
                    content: `
                        <div style="text-align: center; padding: 20px; background-color: #fff; border-radius: 10px; border: 1px solid #f39c12;">
                            <!-- Warning icon similar to SweetAlert -->
                            <div style="font-size: 60px; color: #f39c12; margin-bottom: 20px;">
                                <i class="fa fa-exclamation-circle"></i>
                            </div>
                            <!-- Title in bold with better font -->
                            <h3 style="margin: 0; font-weight: bold; color: #333;">
                                Missing Credentials
                            </h3>
                            <!-- Description message -->
                            <p style="margin-top: 10px; font-size: 16px; color: #555;">
                                Email or Password is missing. Please enter your login credentials.
                            </p>
                        </div>
                    `,
                    type: 'orange', // Orange color type for the warning
                    boxWidth: '400px', // Set the width of the dialog box
                    useBootstrap: false, // Disable Bootstrap for full control of design
                    buttons: {
                        OK: {
                            text: 'OK',
                            btnClass: 'btn-orange',
                            action: function () {
                                // Proceed with the form submission or other logic
                                $('#log').submit();
                            }
                        }
                    },
                    onContentReady: function () {
                        // Apply custom styles to the button
                        this.$content.find('.btn-orange').css({
                            background: '#f39c12',
                            color: '#fff',
                            border: 'none',
                            padding: '10px 20px',
                            fontSize: '16px',
                            fontWeight: 'bold',
                            borderRadius: '5px',
                            cursor: 'pointer',
                            //boxShadow: '0 4px 6px rgba(0,0,0,0.1)'
                        });

                        // Hover effect for the button
                        this.$content.find('.btn-orange').hover(function () {
                            $(this).css('background', '#e67e22');
                        }, function () {
                            $(this).css('background', '#f39c12');
                        });
                    }
                });

                return; // Stop further execution if fields are empty
            }

            // If fields are filled, proceed with AJAX
            const loginCredentials = {
                email: email,
                password: password
            };

            const loginCredentialsJson = JSON.stringify(loginCredentials);

            $.ajax({
                url: '/userlogin',
                type: 'POST',
                contentType: 'application/json',
                data: loginCredentialsJson,
                success: function (data) {
                    console.log("User Data Details - ", data);
                    window.location.href = "/secure/home";
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 400) {
                        $.alert({
                            title: '',
                            content: `
                                <div style="text-align: center; padding: 20px; background-color: #fff; border-radius: 10px; border: 1px solid #e74c3c;">
                                    <!-- Error icon similar to SweetAlert -->
                                    <div style="font-size: 60px; color: #e74c3c; margin-bottom: 20px;">
                                        <i class="fa fa-times-circle"></i>
                                    </div>
                                    <!-- Title in bold with better font -->
                                    <h3 style="margin: 0; font-weight: bold; color: #333;">
                                        Invalid Credentials
                                    </h3>
                                    <!-- Description message -->
                                    <p style="margin-top: 10px; font-size: 16px; color: #555;">
                                        Username or password is incorrect. Please check your credentials and try again.
                                    </p>
                                </div>
                            `,
                            type: 'red', // Red color type for the error
                            boxWidth: '400px', // Set the width of the dialog box
                            useBootstrap: false, // Disable Bootstrap for full control of design
                            buttons: {
                                OK: {
                                    text: 'OK',
                                    btnClass: 'btn-red',
                                    action: function () {
                                        // Close the alert or perform additional logic if needed
                                        console.log('OK button clicked');
                                    }
                                }
                            },
                            onContentReady: function () {
                                // Apply custom styles to the button
                                this.$content.find('.btn-red').css({
                                    background: '#e74c3c',
                                    color: '#fff',
                                    border: 'none',
                                    padding: '10px 20px',
                                    fontSize: '16px',
                                    fontWeight: 'bold',
                                    borderRadius: '5px',
                                    cursor: 'pointer',
                                    boxShadow: '0 4px 6px rgba(0,0,0,0.1)'
                                });

                                // Hover effect for the button
                                this.$content.find('.btn-red').hover(function () {
                                    $(this).css('background', '#c0392b');
                                }, function () {
                                    $(this).css('background', '#e74c3c');
                                });
                            }
                        });
                    } else if (jqXHR.status === 409) {
                        $.alert({
                            title: 'Password Expired!',
                            content: "Your password is expired. Please reset the password.",
                            type: 'orange',
                            buttons: {
                                ok: {
                                    text: 'Reset Password',
                                    action: function () {
                                        window.location.href = "/public/resetexpiredpassword";
                                    }
                                }
                            }
                        });
                    } else {
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
