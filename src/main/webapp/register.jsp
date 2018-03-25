<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 11/22/2017
  Time: 9:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Quickbooks integration CRUD application">
    <meta name="author" content="Pezesha">

    <!-- App title -->
    <title>Prof. Musili Wambua & Co. Advocates Library</title>

    <!-- App CSS -->
    <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>

    <script src="assets/js/modernizr.min.js"></script>

</head>
<body>
<div class="account-pages"></div>
<div class="clearfix"></div>
<div class="wrapper-page">
    <div class="account-bg">
        <div class="card-box m-b-0">
            <div class="text-xs-center m-t-20">
                <a href="#" class="logo">
                    <span>Prof. Musili Wambua & Co. Advocates Library</span>
                </a>
            </div>
            <div class="m-t-10 p-20">
                <div class="row">
                    <div class="col-xs-12 text-xs-center">
                        <h6 class="text-muted text-uppercase m-b-0 m-t-0">Sign Up</h6>
                    </div>
                </div>
                <form class="m-t-20" action="<%=request.getContextPath()%>/register" method="post" id="form-register">
                    <div class="form-group row">
                        <div class="col-xs-12">
                            <input class="form-control" type="email" required="" placeholder="Email"
                                   name="email" id="email">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-12">
                            <input class="form-control" type="password" required="" placeholder="Password"
                                   name="password" id="password">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-12">
                            <input class="form-control" type="password" placeholder="Confirm Password "
                            name="confirmPassword" id="confirmPassword">
                        </div>
                    </div>
                    <div class="form-group row text-center m-t-10">
                        <div class="col-xs-12">
                            <button class="btn btn-success btn-block waves-effect waves-light" type="submit">Register Now</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- end card-box-->
    <div class="m-t-20">
        <div class="text-xs-center">
            <p class="text-white">Already have account? <a href="<%=request.getContextPath()%>/sign-in" class="text-white m-l-5"><b>Sign In</b> </a></p>
        </div>
    </div>
</div>
<!-- end wrapper page -->
<input type="hidden" id="base_url" value="<%=request.getContextPath()%>/"/>
</body>
</html>
<script type="text/javascript" src="assets/plugins/jquery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="assets/js/tether.min.js"></script><!-- Tether for Bootstrap -->
<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="assets/custom/scripts.js"></script>
<script type="text/javascript" src="assets/custom/bootbox.min.js"></script>


<script type="text/javascript">

    var minimum = parseInt($('#minimum').val());
    var lowercase = $('#lowercase').val();
    var uppercase = $('#uppercase').val();
    var numbers = $('#numbers').val();

    $(document).ready(function() {

        $('#form-register').bootstrapValidator(
            {
                message : 'This value is not valid',
                feedbackIcons : {
                    valid : 'glyphicon glyphicon-ok',
                    invalid : 'glyphicon glyphicon-remove',
                    validating : 'glyphicon glyphicon-refresh'
                },
                excluded: ':disabled',
                fields : {
                    email : {
                        validators: {
                            notEmpty: {
                                message: 'The email address is required'
                            },
                            regexp: {
                                regexp: '^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$',
                                message: 'The value is not a valid email address'
                            }
                        }
                    },
                    Password : {
                        validators : {
                            notEmpty : {
                                message : 'Please enter your password'
                            },
                            identical : {
                                field : 'confirmPassword',
                                message : 'Your passwords must match'
                            },
                            callback: {
                                message: 'Invalid password entered',
                                callback: function (value, validator, $field) {
                                    if (value === '') {
                                        return true;
                                    }

                                    // Check the password strength
                                    if (value.length < minimum && minimum > 0) {
                                        console.log("minimum....");
                                        return {
                                            valid: false,
                                            message: 'It must be at least ' + minimum + ' characters long'
                                        };
                                    }

                                    // The password doesn't contain any uppercase character
                                    if (value === value.toLowerCase() && uppercase == "true") {
                                        console.log("uppercase....");
                                        return {
                                            valid: false,
                                            message: 'It must contain at least one upper case character'
                                        }
                                    }

                                    // The password doesn't contain any uppercase character
                                    if (value === value.toUpperCase() && lowercase == "true") {
                                        console.log("lowercase....");
                                        return {
                                            valid: false,
                                            message: 'It must contain at least one lower case character'
                                        }
                                    }

                                    // The password doesn't contain any digit
                                    if (value.search(/[0-9]/) < 0 && numbers == "true") {

                                        console.log("numbers....");
                                        return {
                                            valid: false,
                                            message: 'It must contain at least one digit'
                                        }
                                    }

                                    return true;
                                }
                            }
                        }
                    },
                    confirmPassword : {
                        validators : {
                            notEmpty : {
                                message : 'Please confirm your password'
                            },
                            identical : {
                                field : 'password',
                                message : 'Your passwords must match'
                            },
                            callback: {
                                message: 'Invalid password entered',
                                callback: function (value, validator, $field) {
                                    if (value === '') {
                                        return true;
                                    }

                                    // Check the password strength
                                    if (value.length < minimum && minimum > 0) {
                                        console.log("minimum....");
                                        return {
                                            valid: false,
                                            message: 'It must be at least ' + minimum + ' characters long'
                                        };
                                    }

                                    // The password doesn't contain any uppercase character
                                    if (value === value.toLowerCase() && uppercase == "true") {
                                        console.log("uppercase....");
                                        return {
                                            valid: false,
                                            message: 'It must contain at least one upper case character'
                                        }
                                    }

                                    // The password doesn't contain any uppercase character
                                    if (value === value.toUpperCase() && lowercase == "true") {
                                        console.log("lowercase....");
                                        return {
                                            valid: false,
                                            message: 'It must contain at least one lower case character'
                                        }
                                    }

                                    // The password doesn't contain any digit
                                    if (value.search(/[0-9]/) < 0 && numbers == "true") {

                                        console.log("numbers....");
                                        return {
                                            valid: false,
                                            message: 'It must contain at least one digit'
                                        }
                                    }

                                    return true;
                                }
                            }
                        }
                    }
                }
            }).on(
                'success.form.bv',
                function(e) {
                    start_wait();
                    // Prevent form submission
                    e.preventDefault();

                    console.log("Base url :::::::::::::> " + $('#base_url').val());

                    // Get the form instance
                    $.ajax({
                            url : $('#base_url').val() + 'register',
                            type : 'post',
                            data : {
                                email : $('#email').val(),
                                password : $('#password').val()
                            },
                            dataType : 'json',
                            success : function(json) {
                                stop_wait();
                                if(json.success)
                                {
                                    $("form#form-register")[0].reset();
                                    setTimeout(
                                        function() {
                                            window.location.href = $('#base_url').val() + 'sign-in';
                                        }, 5000);
                                }
                                bootbox.alert('<p class="text-center">' + json.message + '</p>');
                            }
                        });
                });

    });

</script>
