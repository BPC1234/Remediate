<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%
    final String contextPath = request.getContextPath();
%>

<title>BPC</title>
<div class="container-fluid loginBackground2">
 <%--   <div id="bg">
        <img src="<%= contextPath%>/resources/images/BPC2.png" alt="">
    </div>--%>
    <div class="loginDiv">
    <table>
        <tr>
            <td class="leftPadding" valign="top" style="width: 50%;border-right: 1px solid #E3E3E3">
                <div class="leftPart">
                        <h3 class="customH3">Sign in to manage your BPC ID.</h3>
                        <p class="intro">
                            <spring:message code="login.left.paragraph.second"/>
                        <p class="intro">

                            <spring:message code="login.left.paragraph.first"/>
                     </div>
            </td>
            <td class="leftPadding" valign="top">
                    <form id="loginForm" name="loginForm" class=""  method="post" action="j_spring_security_check">
                        <h3 class="customH3"><spring:message code="login.button.submit"/></h3>
                        <div class="input-prepend loginInputDiv">
                            <span id="userNamrIconId" class="add-on"><i class="icon-user"></i></span>
                            <input type="text" placeholder="BPC ID" id="login_username"  name='j_username' class="field required leftPaddingForText span4"
                                   title="Please provide your username"value='<c:if test="${not empty error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
                        </div>
                        <div >
                            <div class="input-prepend loginInputDiv">
                                <span id="passwordIconId" class="add-on"><i class="icon-lock"></i></span>
                                <input type='password' placeholder="Password" id="login_password" class="field required leftPaddingForText span4" name='j_password'/>
                                <div class="signInRow">
                                    <div><h1></h1></div>
                                    <div><a id="forgetPassword" href="#"><spring:message code="login.lost.your.password"/></a></div>
                                </div>
                                <%--<a href=""><span class="add-on" id="login" onclick=""><i class="icon-arrow-right"></i></span></a>--%>

                            </div>
                            <div id="errorId"><font color="red">${error}</font></div>
                        </div>
                    </form>
                <div id="loginButton">
                    <button id="login" type="button" class="btn btn-primary roundCorner">Sign in</button>
                </div>
            </td>

        </tr>

    </table>




    </div>



<%--    <div class="signInRow">
        <div><h1></h1></div>
        <div><a id="forgetPassword" href="#"><spring:message code="login.lost.your.password"/></a></div>
    </div>--%>

</div>

<script>
    $('#login_username').change(function(){
        if($('#login_username').val() != "") {
            $('#errorId').html('');
        }
    });
    $('#forgetPassword').click(function () {
        if($('#login_username').val() == "") {
            $('#errorId').html('<font color="red">Enter BPC ID</font>');
        } else {
            var urlForget = "admin/sendAnEmail.html?name=" + $('#login_username').val();
            $('#forgetPassword').attr('href', urlForget);
        }
    });

</script>

    <%--<c:if test="${not empty error}">
        <br>
        <b><font color="red">
                ${error}
        </font></b>
    </c:if>--%>


 <script>

     $("#contentWrapper").show();
     $('#login_password,#login_username').keypress(function(e){
         if(e.which == 13){//Enter key pressed
             $('#login').click();//Trigger search button click event
         }

     });
     $('#login_username').focus();
 </script>
