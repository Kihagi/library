<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 3/24/2018
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid section">
    <input type="hidden" id="type" value="${ type }" />

<c:if test="${ type == 'EDIT' }">
    <c:forEach var="book" items="${ books }">
        <input type="hidden" name="ACTION" id="ACTION" value="EDIT_BOOK" />
        <input type="hidden" id="book_id" value="${ book.id }" />

        <div class="form-group">
            <label for="title" class="col-sm-5 control-label">Title:</label>
            <div class="col-sm-7">
                <input type="text" name="title" id="title" placeholder="Title" class="form-control  input-sm" value="${ book.title }" />
            </div>
        </div>
        <div class="form-group">
            <label for="author" class="col-sm-5 control-label">Author:</label>
            <div class="col-sm-7">
                <input type="text" name="author" id="author" placeholder="Author" class="form-control  input-sm" value="${ book.author }" />
            </div>
        </div>
        <div class="form-group">
            <label for="edition" class="col-sm-5 control-label">Edition:</label>
            <div class="col-sm-7">
                <input type="text" name="edition" id="edition" placeholder="Edition" class="form-control  input-sm" value="${ book.edition }" />
            </div>
        </div>
        <div class="form-group">
            <label for="publisher" class="col-sm-5 control-label">Publisher:</label>
            <div class="col-sm-7">
                <input type="text" name="publisher" id="publisher" placeholder="Publisher" class="form-control  input-sm" value="${ book.publisher }" />
            </div>
        </div>

        <div class="form-group">
            <label for="isbn" class="col-sm-5 control-label">ISBN:</label>
            <div class="col-sm-7">
                <input type="text" name="isbn" id="isbn" placeholder="ISBN" class="form-control  input-sm" value="${ book.isbn }" />
            </div>
        </div>

        <div class="form-group">
            <label for="category" class="col-sm-5 control-label">Category:</label>
            <div class="col-sm-7">
                <input type="text" name="category" id="category" placeholder="Category" class="form-control  input-sm" value="${ book.category }" />
            </div>
        </div>

        <div class="form-group">
            <label for="status" class="col-sm-5 control-label">Book Status:</label>
            <div class="col-sm-7">
                <select name="status" id="status" class="form-control input-sm" value="${ book.status }">
                    <c:set var="status" value="${ ben.mstatus}"/>
                    <option hidden >${status}</option>
                    <option ${ book.status == 'Available' ? 'selected="selected"' : '' }>Available</option>
                    <option ${ book.status == 'Borrowed' ? 'selected="selected"' : '' }>Borrowed</option>
                </select>
            </div>
        </div>

    </c:forEach>
</c:if>

<c:if test="${ type == 'ADD' }">
    <input type="hidden" name="ACTION" id="ACTION" value="EDIT_BOOK" />

    <div class="form-group">
        <label for="title" class="col-sm-5 control-label">Title:</label>
        <div class="col-sm-7">
            <input type="text" name="title" id="title" placeholder="Title" class="form-control  input-sm" />
        </div>
    </div>
    <div class="form-group">
        <label for="author" class="col-sm-5 control-label">Author:</label>
        <div class="col-sm-7">
            <input type="text" name="author" id="author" placeholder="Author" class="form-control  input-sm" />
        </div>
    </div>
    <div class="form-group">
        <label for="edition" class="col-sm-5 control-label">Edition:</label>
        <div class="col-sm-7">
            <input type="text" name="edition" id="edition" placeholder="Edition" class="form-control  input-sm" />
        </div>
    </div>
    <div class="form-group">
        <label for="publisher" class="col-sm-5 control-label">Publisher:</label>
        <div class="col-sm-7">
            <input type="text" name="publisher" id="publisher" placeholder="Publisher" class="form-control  input-sm"/>
        </div>
    </div>

    <div class="form-group">
        <label for="isbn" class="col-sm-5 control-label">ISBN:</label>
        <div class="col-sm-7">
            <input type="text" name="isbn" id="isbn" placeholder="ISBN" class="form-control  input-sm"/>
        </div>
    </div>

    <div class="form-group">
        <label for="category" class="col-sm-5 control-label">Category:</label>
        <div class="col-sm-7">
            <input type="text" name="category" id="category" placeholder="Category" class="form-control  input-sm" />
        </div>
    </div>

    <div class="form-group">
        <label for="status" class="col-sm-5 control-label">Book Status:</label>
        <div class="col-sm-7">
            <select name="status" id="status" class="form-control input-sm">
                <option>Available</option>
                <option>Borrowed</option>
            </select>
        </div>
    </div>
</c:if>
</div>
