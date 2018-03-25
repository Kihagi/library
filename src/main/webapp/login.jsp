<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 11/21/2017
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Quickbooks integration CRUD application">
    <meta name="author" content="Pezesha">

    <!-- App title -->
    <title>Prof. Musili Wambua & Co. Advocates Library</title>

    <!-- App CSS -->
    <link href="assets/css/style.css" rel="stylesheet" type="text/css" />

    <script src="assets/js/modernizr.min.js"></script>

</head>

<body>
<div class="account-pages"></div>
<div class="clearfix"></div>
<div class="wrapper-page">
    <div class="account-bg">
        <div class="card-box m-b-0">
            <div class="text-xs-center m-t-20">
                <a href="<%=request.getContextPath()%>" class="logo">
                    <span>Prof. Musili Wambua & Co. Advocates Library</span>
                </a>
            </div>
            <div class="m-t-10 p-20">
                <div class="row">
                    <div class="col-xs-12 text-xs-center">
                        <h6 class="text-muted text-uppercase m-b-0 m-t-0">Sign In</h6>
                    </div>
                </div>
                <form class="m-t-20" action="<%=request.getContextPath()%>/sign-in" method="post" id="form-log-in">
                    <div class="form-group row">
                        <div class="col-xs-12">
                            <input class="form-control" type="text" required="" placeholder="Username"
                                   name="username" id="username">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-12">
                            <input class="form-control" type="password" required="" placeholder="Password"
                                   name="password" id="password">
                        </div>
                    </div>
                    <div class="form-group text-center row m-t-10">
                        <div class="col-xs-12">
                            <button class="btn btn-success btn-block waves-effect waves-light" type="submit">Log In</button>
                        </div>
                    </div>
                    <div class="form-group row m-t-30 m-b-0">
                        <div class="col-sm-12">
                            <a href="javascript:void(0);" class="text-muted" id="pwd-reset-btn"><i class="fa fa-lock m-r-5"></i> Forgot your password?</a>
                        </div>
                    </div>
                </form>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="m-t-20">
        <div class="text-xs-center">
            <p class="text-white">Don't have an account? <a href="<%=request.getContextPath()%>/register" class="text-white m-l-5"><b>Sign Up</b></a></p>
        </div>
    </div>
</div>
<input type="hidden" id="base_url" value="<%=request.getContextPath()%>/" />
</body>
</html>

<script type="text/javascript" src="assets/plugins/jquery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="assets/js/tether.min.js"></script><!-- Tether for Bootstrap -->
<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="assets/custom/scripts.js"></script>
<script type="text/javascript" src="assets/custom/bootbox.min.js"></script>

<script type="text/javascript">

    $(document).ready(function() {

        $('#form-log-in')
            .bootstrapValidator(
                {
                    message : 'This value is not valid',
                    feedbackIcons : {
                        valid : 'glyphicon glyphicon-ok',
                        invalid : 'glyphicon glyphicon-remove',
                        validating : 'glyphicon glyphicon-refresh'
                    },
                    excluded: ':disabled',
                    fields : {
                        username : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter your Email'
                                }
                            }
                        },
                        password : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter your password'
                                }
                            }
                        }
                    }
                })
            .on(
                'success.form.bv',
                function(e) {
                    start_wait();
                    // Prevent form submission
                    e.preventDefault();
                    // Get the form instance
                    var btn = "btn-sign-in";
                    var form = "form-log-in";
                    var modal = "modal-sign-in";
                    var btn_text = $('#' + btn).val();

                    $.ajax({
                        url : $('#base_url').val() + 'sign-in',
                        type : 'post',
                        data : {
                            username : $(
                                '#username')
                                .val(),
                            password : $(
                                '#password')
                                .val()
                        },
                        dataType : 'json',
                        success : function(json) {
                            stop_wait();
                            var message = null;
                            var success = false;
                            $ .each(json, function(key, value) {
                                    if (key == 'success' && value == true) {
                                        success = true;
                                        $("form#" + form)[0].reset();
                                        window.location.href = $('#base_url').val() + 'dashboard';
                                    }
                                    if (key == 'message') {
                                        message = value;
                                    }
                                });
                            if (message == null) {
                                message = 'Oops! We are sorry, but something unexpected just went wrong. Please try again';
                            }
                            $('#' + modal).modal('hide');
                            $('#' + btn).val(
                                btn_text);
                            if (!success)
                                bootbox.alert('<p class="text-center">' + message + '</p>');
                        }
                    });
                });
    });
</script>