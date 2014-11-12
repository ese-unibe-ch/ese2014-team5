<%-- 
    Document   : Registration of account
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp" />

<div class="reservation">

<legend>Sign up now, it's free!</legend>
    <c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
            ${page_error}
        </div>
    </c:if>

    <form:form method="post" modelAttribute="signupUser" action="createAccount" id="signupUser" cssClass="form-horizontal"
               autocomplete="off">
        <fieldset>
            Enter Your Name, Email and your password<br/>

            <c:set var="FirstNameErrors"><form:errors path="FirstName"/></c:set>
            <div class="control-group<c:if test="${not empty firstNameErrors}"> error</c:if>">
                    <label class="control-label" for="field-FirstName">First Name</label>
                    <div class="controls">
                    <form:input path="FirstName" id="field-FirstName" tabindex="2" maxlength="35" placeholder="First Name"/>
                    <form:errors path="FirstName" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="LastNameErrors"><form:errors path="LastName"/></c:set>
            <div class="control-group<c:if test="${not empty lastNameErrors}"> error</c:if>">
                    <label class="control-label" for="field-LastName">Last Name</label>
                    <div class="controls">
                    <form:input path="LastName" id="field-LastName" tabindex="2" maxlength="35" placeholder="Last Name"/>
                    <form:errors path="LastName" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="EmailErrors"><form:errors path="Email"/></c:set>
            <div class="control-group<c:if test="${not empty emailErrors}"> error</c:if>">
                    <label class="control-label" for="field-Email">Email</label>

                    <div class="controls">
                    <form:input path="Email" id="field-Email" tabindex="2" maxlength="45" placeholder="Email"/>
                    <form:errors path="Email" cssClass="help-inline" element="span"/>
                </div>
            </div>


            <c:set var="passwordErrors"><form:errors path="password"/></c:set>
            <div class="control-group<c:if test="${not empty passwordErrors}"> error</c:if>">
                    <label class="control-label" for="field-password">Password</label>
                    <div class="controls">
                    <form:input type="password" path="password" id="field-password" tabindex="2" maxlength="35" placeholder="password"/>
                    <form:errors path="password" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="passwordRepeatErrors"><form:errors path="passwordRepeat"/></c:set>
            <div class="control-group<c:if test="${not empty passwordRepeatErrors}"> error</c:if>">
                    <label class="control-label" for="field-passwordRepeat">PasswordRepeat</label>
                    <div class="controls">
                    <form:input type="password" path="passwordRepeat" id="field-passwordRepeat" tabindex="2" maxlength="35" placeholder="passwordRepeat"/>
                    <form:errors path="passwordRepeat" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Sign up</button>
                <button type="button" class="btn">Cancel</button>
            </div>


        </fieldset>
    </form:form>

</div>
</div>
<div class="clear"></div>
</div>

<c:import url="template/footer.jsp" />
