<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 11/22/2017
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="partial/header.jsp"/>

<div class="wrapper">
    <div class="container">

        <!-- Page-Title -->
        <div class="row">
            <div class="col-sm-12">
                <h4 class="page-title">Library Stats</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-12 col-md-6 col-lg-6 col-xl-4">
                <div class="card-box tilebox-one">
                    <i class="icon-list pull-xs-right text-muted"></i>
                    <h6 class="text-muted text-uppercase m-b-20">Total Number Of Books</h6>
                    <h2 class="m-b-20" data-plugin="counterup">${ count }</h2>
                </div>
            </div>

            <div class="col-xs-12 col-md-6 col-lg-6 col-xl-4">
                <div class="card-box tilebox-one">
                    <i class="icon-share-alt pull-xs-right text-muted"></i>
                    <h6 class="text-muted text-uppercase m-b-20">Borrowed Books</h6>
                    <h2 class="m-b-20" data-plugin="counterup">${ borrowed }</h2>
                </div>
            </div>

            <div class="col-xs-12 col-md-6 col-lg-6 col-xl-4">
                <div class="card-box tilebox-one">
                    <i class="icon-chart pull-xs-right text-muted"></i>
                    <h6 class="text-muted text-uppercase m-b-20">Available Books</h6>
                    <h2 class="m-b-20" data-plugin="counterup">${ available }</h2>
                </div>
            </div>
        </div>
        <!-- end row -->

        <div class="modal fade" id="wait-dialog" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-body">
                <p class="text-center masked-bg"><img src="assets/images/wait.gif" alt="Please wait..."/><br/>Creating Journal
                    Entries<br>Please wait...</p>
            </div>
        </div>

        <script type="text/javascript" src="assets/plugins/jquery/jQuery-2.1.4.min.js"></script>
        <script type="text/javascript" src="assets/plugins/datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="assets/js/tether.min.js"></script>
        <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="assets/js/bootstrapValidator.min.js"></script>
        <script type="text/javascript" src="assets/custom/scripts.js"></script>
        <script type="text/javascript" src="assets/custom/bootbox.min.js"></script>
        <script type="text/javascript" src="assets/plugins/moment/moment.js"></script>
        <script type="text/javascript" src="assets/plugins/timepicker/bootstrap-timepicker.min.js"></script>
        <script type="text/javascript" src="assets/plugins/mjolnic-bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>
        <script type="text/javascript" src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript" src="assets/plugins/clockpicker/bootstrap-clockpicker.js"></script>
        <script type="text/javascript" src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script type="text/javascript" src="assets/pages/jquery.form-pickers.init.js"></script>

        <script type="text/javascript">

            function importCustomers() {
                start_wait();
                $.ajax({
                    url: $('#base_url').val() + 'importCustomers',
                    type: 'post',
                    data: {
                        ACTION: 'IMPORT_CUSTOMERS'
                    },
                    dataType: 'html',
                    success: function (json) {
                        stop_wait();
                        if (json != null) {
                            console.log(json);
                            json = JSON.parse(json);
                            $.each(json, function (key, value) {
                                if (key == 'message') {
                                    console.log(":::::::::: Key is message ::::::::");
                                    html = json.message;
                                    bootbox.alert(html);
                                }
                            });
                        }
                    }
                });
            }

        </script>

<jsp:include page="partial/footer.jsp"/>