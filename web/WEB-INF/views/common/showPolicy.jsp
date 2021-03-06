<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>

<div>
    <div class="row-fluid marginLeft">

        <div class="span6">
            <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
            <div class="containerHeadline">
                <i class="icon-ok-sign"></i><h2>Policy</h2>
                <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
            </div>
            <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

            <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
            <div class="floatingBox">
                        <div class="container-fluid">
                            <h4>Policy content</h4>
                            <p>
                                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae  Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam,
                            </p>
                           <p>
                               quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
                           </p>
                           <p>
                               ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.
                           </p>
                            <div class="pull-right analyzeByControlSubmitDiv">
                                <button style="width: 111px;" class="btn btn-warning" type="button"><i class="icon-remove"></i>&nbsp;&nbsp;Not now</button>
                                <button id="understandButton" style="width: 111px;" class="btn btn-success" type="button"><i class="icon-ok"></i>&nbsp;&nbsp;Understand</button>
                            </div>
                </div>
            </div>
            <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
        </div>
    </div>
    <!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
</div>
<script>
    $(document).ready(function(){
       $("#understandButton").click(function(){
           window.location = "./underStandPolicy.html?policyId=${policyId}";
       });
    });
</script>