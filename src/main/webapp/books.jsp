<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 3/24/2018
  Time: 1:55 PM
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
                <h4 class="page-title">Books</h4>
            </div>
        </div>

            <!-- Add New Book -->
        <div class="row">
            <div class="col-xs-12">
                <div class="card-box">
                    <h4 class="header-title m-t-0">Add New Book</h4>
                    <p class="text-muted font-13 m-b-10">
                        Click the button below to Add a new book
                    </p>
                    <button class="btn btn-primary waves-effect waves-light" onclick="add_book();">
                        <i class="fa fa-book"></i>&nbsp; NEW BOOK</button>
                </div>
            </div>
        </div>

            <!-- List Books -->
        <div class="row">
            <div class="col-xs-12">
                <div class="card-box">

                    <div class="row">
                        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
                            <h4 class="header-title m-t-0">All Books</h4>
                            <p class="text-muted font-13 m-b-10">
                                Edit or Delete Book
                            </p>

                            <div class="p-20">
                                <table class="table" id="books_list">
                                    <thead class="thead-default">
                                    <tr>
                                        <th>#</th>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>ISBN</th>
                                        <th>Edition</th>
                                        <th>Publisher</th>
                                        <th>Category</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="book" items="${books}">
                                    <tr>
                                        <td>${ book.id }</td>
                                        <td>${ book.title }</td>
                                        <td>${ book.author }</td>
                                        <td>${ book.isbn }</td>
                                        <td>${ book.edition }</td>
                                        <td>${ book.publisher }</td>
                                        <td>${ book.category }</td>
                                        <td>${ book.status }</td>
                                        <td>
                                            <a class="btn btn-primary-outline waves-effect waves-light" href="javascript:void(0);"  onclick="edit_book('${ book.id }')">
                                                <i class="fa fa-edit "></i>&nbsp; EDIT</a>&nbsp;&nbsp;

                                            <button class="btn btn-sm btn-danger" onclick="delete_book('${ book.id }', '${ book.title }');">
                                                <i class="fa fa-edit "></i>&nbsp; DELETE</button>

                                        </td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>

                                </table>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    <!-- end row -->

    </div> <!-- container -->
</div> <!-- End wrapper -->

<div class="modal fade"  id="modal-edit-book" tabindex="-1" role="dialog" aria-labelledby="myModalLabelEditBooks" aria-hidden="true">
    <form role="form" id="form-edit-book" enctype="multipart/form-data">
        <div class="modal-dialog large-modal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="myModalLabelEditBooks">
                        <i class="fa fa-edit"></i>&nbsp;&nbsp;BOOK DETAILS
                    </h4>
                </div>
                <div class="modal-body" id="book-content">
                </div>

                <div class="modal-footer">
                    <a href="#" class="btn btn-warning" data-dismiss="modal">Cancel</a>
                    <input class="btn btn-primary" type="submit"
                           value="Save Book Details" id="btn-book">
                </div>
            </div>
        </div>
    </form>
    <input type="hidden" id="base_url" value="<%=request.getContextPath()%>/" />
</div>

<script type="text/javascript" src="assets/plugins/jquery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="assets/js/tether.min.js"></script><!-- Tether for Bootstrap -->
<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="assets/custom/scripts.js"></script>
<script type="text/javascript" src="assets/custom/bootbox.min.js"></script>

<!-- Required datatable js -->
<script src="assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="assets/plugins/datatables/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">

    function edit_book(id)
    {
        start_wait();
        $.ajax({
            url: $('#base_url').val() + 'book',
            type: 'post',
            data: {ACTION:'FETCH_BOOK_DETAILS', bookId: id, type: 'EDIT'},
            dataType: 'html',
            success: function(html) {
                stop_wait();
                $('#modal-edit-book').modal('show');
                $('#book-content').html(html);
            }
        });
    }

    function add_book()
    {
        start_wait();
        $.ajax({
            url: $('#base_url').val() + 'book',
            type: 'post',
            data: {ACTION:'FETCH_BOOK_DETAILS', type: 'ADD'},
            dataType: 'html',
            success: function(html) {
                stop_wait();
                $('#modal-edit-book').modal('show');
                $('#book-content').html(html);
            }
        });
    }

    function delete_book(id, title)
    {
        bootbox.confirm("<p class=\"text-center\">You are about to delete <strong>\"" + title + "\"</strong>. Are you sure?</p>", function(result){
            if(result)
            {
                start_wait();
                $.ajax({
                    url: $('#base_url').val() + 'book',
                    type: 'post',
                    data: {ACTION:'DELETE_BOOK', bookId: id},
                    dataType: 'json',
                    success: function(json) {
                        bootbox.alert("<p class=\"text-center\">" + json.message + "</p>");
                        stop_wait();
                        window.location.replace("<%=request.getContextPath()%>/book");
                    }
                });
            }
        });
    }

    $(document).ready(function(){
        start_wait();
        $('#books_list').DataTable({

        });
        stop_wait();

        $('#form-edit-book')
            .bootstrapValidator(
                {
                    excluded: ':disabled',
                    message : 'This value is not valid',
                    feedbackIcons : {
                        valid : 'glyphicon glyphicon-ok',
                        invalid : 'glyphicon glyphicon-remove',
                        validating : 'glyphicon glyphicon-refresh'
                    },
                    fields : {
                        title : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the title'
                                }
                            }
                        },
                        author : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the Author'
                                }
                            }
                        },
                        edition : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the Edition'
                                }
                            }
                        },
                        publisher : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the Publisher'
                                }
                            }
                        },
                        isbn : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the ISBN'
                                }
                            }
                        },
                        category : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the Category'
                                }
                            }
                        },
                        status : {
                            validators : {
                                notEmpty : {
                                    message : 'Please enter the Status'
                                }
                            }
                        }
                    }
                })
            .on( 'success.form.bv', function(e) {
                start_wait();
                // Prevent form submission
                e.preventDefault();

                // Get the form instance
                var modal = "modal-edit-book";

                console.log("Book Id :::::::: " + $('#book_id').val());
                console.log("Book Name :::::::: " + $('#title').val());
                console.log("Type :::::::: " + $('#type').val());
                console.log("Status :::::::: " + $('#status').val());

                $.ajax({
                    url: $('#base_url').val() + 'book',
                    type: 'post',
                    data: {ACTION:$('#ACTION').val(), type: $('#type').val(), bookId: $('#book_id').val(), title: $('#title').val(), author : $('#author').val(), edition : $('#edition').val(), publisher : $('#publisher').val(), isbn : $('#isbn').val(), category : $('#category').val(), status : $('#status').val()},
                    dataType : 'json',
                    success : function(json) {
                        stop_wait();
                        if(json.success)
                        {
                            $('form#form-edit-book')[0].reset();
                            $('#modal-edit-book').modal('hide');
                            location.reload();
                        }
                        bootbox
                            .alert('<p class="text-center">'
                                + json.message
                                + '</p>');
                    }
                });

            });
    });

</script>

<jsp:include page="partial/footer.jsp"/>
