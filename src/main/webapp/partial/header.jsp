<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 11/22/2017
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
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

    <link href="assets/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet">
    <link href="assets/plugins/mjolnic-bootstrap-colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
    <link href="assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="assets/plugins/clockpicker/bootstrap-clockpicker.min.css" rel="stylesheet">
    <link href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

    <script type="text/javascript" src="assets/plugins/jquery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" src="assets/js/tether.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="assets/custom/scripts.js"></script>
    <script type="text/javascript" src="assets/custom/bootbox.min.js"></script>

    <![endif]-->
    <!-- Modernizr js -->
    <script src="assets/js/modernizr.min.js"></script>

</head>
<body>
<!-- Navigation Bar-->
<header id="topnav">
    <div class="topbar-main">
        <div class="container">
            <!-- LOGO -->
            <div class="topbar-left">
                <a href="index.html" class="logo">
                    <span>Prof. Musili Wambua & Co. Advocates Library</span>
                </a>
            </div>
            <!-- End Logo container-->

            <div class="menu-extras">
                <ul class="nav navbar-nav pull-right">
                    <li class="nav-item">
                        <!-- Mobile menu toggle-->
                        <a class="navbar-toggle">
                            <div class="lines">
                                <span></span>
                                <span></span>
                                <span></span>
                            </div>
                        </a>
                        <!-- End mobile menu toggle-->
                    </li>
                    <li class="nav-item hidden-sm-down">
                        <form role="search" class="navbar-left app-search pull-left hidden-xs">
                            <input type="text" placeholder="Search..." class="form-control">
                            <a href=""><i class="fa fa-search"></i></a>
                        </form>
                    </li>
                    <li class="nav-item dropdown notification-list">
                        <a class="nav-link dropdown-toggle arrow-none waves-effect waves-light nav-user" data-toggle="dropdown" href="#" role="button"
                           aria-haspopup="false" aria-expanded="false">
                            <img src="assets/images/avatar-4.jpg" alt="user" class="img-circle">
                        </a>
                        <div class="dropdown-menu dropdown-menu-right dropdown-arrow profile-dropdown " aria-labelledby="Preview">
                            <!-- item-->
                            <div class="dropdown-item noti-title">
                                <h5 class="text-overflow"><small>Welcome! Pauline</small> </h5>
                            </div>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="zmdi zmdi-account-circle"></i> <span>Profile</span>
                            </a>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="zmdi zmdi-settings"></i> <span>Settings</span>
                            </a>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="zmdi zmdi-lock-open"></i> <span>Lock Screen</span>
                            </a>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="zmdi zmdi-power"></i> <span>Logout</span>
                            </a>

                        </div>
                    </li>
                </ul>
            </div> <!-- end menu-extras -->
            <div class="clearfix"></div>
        </div> <!-- end container -->
    </div><!-- end topbar-main -->

    <div class="navbar-custom">
        <div class="container">
            <div id="navigation">
                <!-- Navigation Menu-->
                <ul class="navigation-menu">
                    <li>
                        <a href="<%=request.getContextPath()%>/dashboard"><i class="zmdi zmdi-view-dashboard"></i> <span> Dashboard </span> </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/book"><i class="zmdi zmdi-view-dashboard"></i> <span> Books </span> </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/employee"><i class="zmdi zmdi-format-list-bulleted"></i> <span> Employees </span> </a>
                    </li>
                </ul><!-- End navigation menu  -->
            </div>
        </div>
    </div>
</header>

