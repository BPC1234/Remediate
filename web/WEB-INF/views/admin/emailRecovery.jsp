<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="mailsend">
    <div class="row-fluid">
        <div class="span6">
            <!-- ==================== NOTIFICATIONS FLOATING BOX ==================== -->
            <div class="floatingBox">
                <div class="container-fluid">
                    <div class="alert textCss">
                        Email sent to your mail inbox. To get back into your account, follow the instructions we've sent to your email address at <c:out value="${userMail}"/>. If you don't see the email in your inbox, check your spam folder for an email from no-reply@accounts.google.com. If you still don't see the email, contact your administrator for support.
                    </div>

                </div>
            </div>
        </div>

    </div>
    <!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
</div>