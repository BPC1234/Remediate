<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();

%>
<script src="<%= contextPath %>/resources/js/jquery.maphilight.min.js"></script>
<script src="<%= contextPath %>/js/kinetic-v4.5.1.min.js"></script>
<script src="<%= contextPath %>/js/worldMap.js"></script>


<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="container-fluid">
   <div class="row-fluid">
   <div class="span12">
   <a id="countryScoringLink" href="<%= contextPath %>/resources/CountryScoringMethodologyandSummary.xlsx" style="display: none;"></a>
   <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
            <div class="containerHeadline">
                <i class="icon-ok-sign"></i><h2><spring:message code="heat.map"/></h2>
                <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
            </div>
            <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->
            <div class="floatingBox">
            <div class="container-fluid">
            <div id="container">
            <div id="map" class="centerAlign">
            </div>

            <div style="clear: both"></div>
            <div id="percentage">
                <table id="heatMapPercentageTable" style="display: none;">
                    <tr <%--class="filled warning"--%>>
                        <td align="left" class="firstTd"><spring:message code="proactiveProject.cpiScore" />:</td>
                        <td align="right">${proactiveBlockWeightRatio.cpiScore}%</td>
                    </tr>
                    <tr <%--class="filled success"--%>>
                        <td align="left"><spring:message code="proactiveProject.revenues" />:</td>
                        <td align="right">${proactiveBlockWeightRatio.revenues}%</td>
                    </tr>
                    <tr <%--class="filled info"--%>>
                        <td align="left"><spring:message code="proactiveProject.salesModelRelationships" />:</td>
                        <td align="right">${proactiveBlockWeightRatio.salesModelRelationships}%</td>
                    </tr>
                    <tr <%--class="filled danger"--%>>
                        <td align="left"><spring:message code="proactiveProject.governmentInteraction" />:</td>
                        <td align="right">${proactiveBlockWeightRatio.governmentInteraction}%</td>
                    </tr>
                    <tr <%--class="filled label-info"--%>>
                        <td align="left"><spring:message code="proactiveProject.natureOfBusiness" />:</td>
                        <td align="right">${proactiveBlockWeightRatio.natureOfBusinessOperations}%</td>
                    </tr>

                </table>
                </div>
            <div style="display: inline-block; margin-top:80px;margin-left:40px;">
                <button style="display: none;" id="showCountryScoring" type="button" class="btn btn-small btn-primary"><i class="icon-download"></i> Analysis</button>
            </div>
            <div id="legend" style="width: 350px">
                <table id="heatMapLegendTable" style="display: none;">
                    <tr>
                      <td style="width: 80%" align="left" class="firstTd"><spring:message code="heatMap.minimumToMiderateCorruption" /></td>
                      <td align="right">
                          <div class="progress">
                              <div class="bar progress-green" ></div>
                          </div>
                      </td>
                    </tr>

                    <tr>
                      <td align="left"><spring:message code="heatMap.significantCorruption" /></td>
                      <td align="right">
                          <div class="progress">
                          <div class="bar progress-info_warning"></div>
                          </div></td>
                    </tr>

                    <tr>
                      <td align="left"><spring:message code="heatMap.rampantCorruption" /></td>
                      <td align="right">
                          <div class="progress">
                              <div class="bar progress-orange-reddish"></div>
                          </div></td>
                    </tr>
                    <tr>
                      <td align="left"><spring:message code="noData" /></td>
                      <td align="right">
                          <div class="progress">
                              <div class="bar progress-orange-ash"></div>
                          </div></td>
                    </tr>
                    </table>
            </div>


            <div style="clear: both"></div>
           <%-- <div id="country" class="bold">
                <label id="cName"></label><spring:message code="weightedScreen.weightedScore" /> : <label id="wScore"></label>
            </div>--%>

                <div id="country">
                  <table>
                      <tr>
                          <td style="color: white;font-size: 12px!important;font-weight: bold!important;" colspan="2"></td>
                      </tr>
                     <tr>
                          <td class="countryHoverMenu">TI CPI Risk Index :</td>
                          <td class="countryHoverMenu"></td>
                      </tr>
                    <tr>
                          <td class="countryHoverMenu">Annual Revenue Generated for 2013 :</td>
                          <td class="countryHoverMenu"></td>
                      </tr>
                   <tr>
                          <td class="countryHoverMenu">Percentage of Government Revenue :</td>
                          <td class="countryHoverMenu"></td>
                      </tr>
                   <tr>
                          <td class="countryHoverMenu">Sales to Government Customers :</td>
                          <td class="countryHoverMenu"></td>
                      </tr>
                   <tr>
                          <td class="countryHoverMenu">Risk Rating :</td>
                          <td class="countryHoverMenu"></td>
                      </tr>
                  </table>
                </div>
            <div id="country1">
                  <table>
                      <tr>
                          <td style="color: white;font-size: 12px!important;font-weight: bold!important;" colspan="2"></td>
                      </tr>
                     <tr>
                          <td class="countryHoverMenu"></td>
                          <td class="countryHoverMenu"></td>
                      </tr>
                  </table>
                </div>

            <script defer="defer">
            var counter=0; //this is for demo color change only
            var stage = new Kinetic.Stage({
                container: 'map',
                width: 976,
                height: 450
            });
            var mapLayer = new Kinetic.Layer({
                y: 25,
                scale: 1
            });
            var topLayer = new Kinetic.Layer({
                y: 25,
                scale: 1
            });



            /*
             * loop through country data stroed in the worldMap
             * variable defined in the worldMap.js asset
             */
            var oneCounter = 0;
            for(var key1 in worldMap.shapes) {
                var path;
                var layer;
                var star;
                var width;
                var height;
                var proactiveBlockWeightOld;
                ///////////////////////

                <c:forEach var="bc" items="${keyMap}" varStatus="status">
                if('${bc.key}' == key1){
                    <c:forEach var="bc1" items="${bc.value}" varStatus="status1">

                    ///////////////////////// Start of Drawing   ///////////////////
                    proactiveBlockWeightOld = ${bc1.value};
                    if( proactiveBlockWeightOld.tiCpiRiskIndex > 0 && proactiveBlockWeightOld.tiCpiRiskIndex <= 3){
                       if( proactiveBlockWeightOld.tiCpiRiskIndex > 0 && proactiveBlockWeightOld.tiCpiRiskIndex < 1){

                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FFCCCC',  //red
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if( proactiveBlockWeightOld.tiCpiRiskIndex >= 1 && proactiveBlockWeightOld.tiCpiRiskIndex <= 1.4){

                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FFB2B2',  //red
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if( proactiveBlockWeightOld.tiCpiRiskIndex > 1.5 && proactiveBlockWeightOld.tiCpiRiskIndex <= 1.7){

                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FF9999',  //red
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if( proactiveBlockWeightOld.tiCpiRiskIndex > 1.8 && proactiveBlockWeightOld.tiCpiRiskIndex <= 2){

                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FF8080',  //red
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if( proactiveBlockWeightOld.tiCpiRiskIndex > 2.1 && proactiveBlockWeightOld.tiCpiRiskIndex <= 2.7){

                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FF6666',  //red
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if( proactiveBlockWeightOld.tiCpiRiskIndex >= 2.8 && proactiveBlockWeightOld.tiCpiRiskIndex <= 3){

                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FF3333',  //red
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }
                    }else if(proactiveBlockWeightOld.tiCpiRiskIndex > 3 && proactiveBlockWeightOld.tiCpiRiskIndex <= 5){
                       if(proactiveBlockWeightOld.tiCpiRiskIndex > 3 && proactiveBlockWeightOld.tiCpiRiskIndex < 3.5){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FFFFB2',//yellow
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 3.5 && proactiveBlockWeightOld.tiCpiRiskIndex < 4){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FFFF80',//yellow
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 4 && proactiveBlockWeightOld.tiCpiRiskIndex < 4.5){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FFFF4D',//yellow
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 4.5 && proactiveBlockWeightOld.tiCpiRiskIndex <= 5){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#FFFF00',//yellow
                            stroke: 'white',
                            strokeWidth: 1
                        });
                       }
                    }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 5){
                          if(proactiveBlockWeightOld.tiCpiRiskIndex >= 5 && proactiveBlockWeightOld.tiCpiRiskIndex < 6){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#CCE6CC',//green
                            stroke: 'white',
                            strokeWidth: 1
                        });
                         }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 6 && proactiveBlockWeightOld.tiCpiRiskIndex < 7){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#99CC99',//green
                            stroke: 'white',
                            strokeWidth: 1
                        });
                         }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 7 && proactiveBlockWeightOld.tiCpiRiskIndex < 8){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#66B366',//green
                            stroke: 'white',
                            strokeWidth: 1
                        });
                         }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 8 && proactiveBlockWeightOld.tiCpiRiskIndex < 9){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#339933',//green
                            stroke: 'white',
                            strokeWidth: 1
                        });
                         }else if(proactiveBlockWeightOld.tiCpiRiskIndex >= 9 ){
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#008000',//green
                            stroke: 'white',
                            strokeWidth: 1
                        });
                         }
                    }else{
                        path = new Kinetic.Path({
                            data: worldMap.shapes[key1],
                            fill: '#D5CEB3',
                            stroke: 'white',
                            strokeWidth: 1
                        });

                        /*star = new Kinetic.Star({
                            x: 350,//use actual top value for key1 country,can be loaded from an array
                            y: 250,//use actual left value for key1 country
                            numPoints: 5,
                            innerRadius: 3,
                            outerRadius: 7,
                            fill: 'skyblue',
                            stroke: 'black',
                            strokeWidth: 1
                        });*/

                    }

                    ///////////  end of drawing   ////////////////


                    ///////////////////////// Start of CSS   ///////////////////
                    path.on('mouseover', function(e) {
                        proactiveBlockWeightOld =  ${bc1.value};
                        this.setFill('black');
                        this.moveTo(topLayer);
                        topLayer.drawScene();
                        $(".kineticjs-content").css({ "cursor":"pointer"});

                        if(proactiveBlockWeightOld.tiCpiRiskIndex > 0){
                       $("#country table tr").eq(0).find('td').eq(0).text('${bc1.key}');
                       $("#country table tr").eq(1).find('td').eq(1).text(proactiveBlockWeightOld.tiCpiRiskIndex);
                       $("#country table tr").eq(2).find('td').eq(1).text(proactiveBlockWeightOld.annualRevenue);
                       $("#country table tr").eq(3).find('td').eq(1).text(proactiveBlockWeightOld.percentageOfGovRevenue);
                       $("#country table tr").eq(4).find('td').eq(1).text(proactiveBlockWeightOld.salesToGovCustomers);
                       $("#country table tr").eq(5).find('td').eq(1).text(proactiveBlockWeightOld.saleToCpiAndGovCustomers);
                       $("#country").css({"top":e.layerY+100,"left":e.layerX+200,"display":"block"});
                        }
                        else{
                            $("#country1 table tr").eq(0).find('td').eq(0).text('${bc1.key}');
//                            $("#country1 table tr").eq(1).find('td').eq(0).text("No Data");
                            $("#country1").css({"top":e.layerY+130,"left":e.layerX+200,"display":"block"});
                        }


                    });

                    path.on('mouseout', function() {
                        this.setFill('#eee');
                        this.moveTo(mapLayer);
                        topLayer.draw();
                        $(".kineticjs-content").css({ "cursor":""});
                        $("#country").css({"display":"none"});
                        $("#country1").css({"display":"none"});
                    });

                    path.on('click', function() {
                        window.location = "ProactiveWorkflow.html?cName="+'${bc1.key}'+"&wScore="+proactiveBlockWeightOld.weightedScoreStr;
                    });
                    ///////////////////////// End of CSS   ///////////////////
                    </c:forEach>
                }
                </c:forEach>
                oneCounter++;
            /*    if(star!=null){

                    mapLayer.add(path);
                    mapLayer.add(star);
                    star.moveToTop();

                }
                else*/
                    mapLayer.add(path);
                counter++;
            }

            stage.add(mapLayer);
            stage.add(topLayer);
            </script>
            </div>
            <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->


                </div>
            </div>
            <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
        </div>
    </div>
    <!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
</div>
<script>
    $(document).ready(function(){
        $("#heatMapLegendTable").show();
        $("#showCountryScoring").show();
        $("#heatMapPercentageTable").show();

    $(document).on("click","#showCountryScoring",function(){
        document.getElementById("countryScoringLink").click();
    });
    });

</script>

