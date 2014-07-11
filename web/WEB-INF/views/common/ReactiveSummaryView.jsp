<%--
  Created by IntelliJ IDEA.
  User: amjad
  Date: 7/29/13
  Time: 5:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>


<div id="content">
<!-- Left side Tabs Start from here -->
<div id="tabs" style="float:left;">
    <ul>
        <li><a href="#tabs-2"><b>Transaction List</b></a></li>
        <li><a href="#tabs-1"><b>Summary of Third Party Transactions</b></a></li>
        <!--<li><a href="#tabs-3"><b>Transaction Details</b></a></li>-->
    </ul>
    <div id="tabs-1">

        <div class="tableContainer">
            <br/>

            </br>
            <div id="subTableDiv">
                <div>
                    <div>
                        <div>
                            <table id="summeryTable1" class="tablesorter">
                                <thead>
                                <tr>
                                    <th>Rule wise Transaction count</th>
                                    <th>Rule definition</th>
                                    <!-- (with extra rule) -->
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>12</td>
                                    <td>Transaction involving structured payments</td>
                                </tr>
                                <tr>
                                    <td>15</td>
                                    <td>Transaction on weekends or holidays</td>
                                </tr>
                                <tr>
                                    <td>10</td>
                                    <td>Transaction with entity (including narrative of transaction) appearing on "Do
                                        Not Use/Do Not Pay" or "Inactive" lists
                                    </td>
                                </tr>
                                <tr>
                                    <td>30</td>
                                    <td>Transaction with entity (including narrative of transaction) appearing on OFAC
                                        Specially Designated Nationals list
                                    </td>
                                </tr>
                                <tr>
                                    <td>13</td>
                                    <td>Transaction with entity (including narrative of transaction) appearing on
                                        Politically Exposed Persons list
                                    </td>
                                </tr>
                                <tr>

                                    <td>15</td>
                                    <td>Transaction with entity not appearing on "Vendor Master File"/"Employee Master
                                        File"/"Customer Master File"
                                    </td>
                                </tr>
                                <tr>
                                    <td>25</td>
                                    <td>Transaction without associated transaction narrative/description</td>
                                </tr>
                                <tr>
                                    <td>20</td>
                                    <td>Transactions with duplicate document numbers</td>
                                </tr>
                                <tr>
                                    <td>24</td>
                                    <td>Transaction amount equal to approver limit</td>
                                </tr>
                                <tr>
                                    <td>14</td>
                                    <td>Transaction with blank entity name</td>
                                </tr>
                                <tr>
                                    <td>10</td>
                                    <td>Transaction narrative responsive to keyword search</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--<div id="tabs-3">
         <div id="detailSection" style="width:100%">
             <div class="firstColumn" style="float:left;">
                 <div class="detailContent"><div class="contentLabel">Transaction ID : </div><div class="contentValue"><label><b>10101</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Date : </div><div class="contentValue"><label><b>21/05/2013</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Amount : </div><div class="contentValue"><label><b>$20,000</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Region : </div><div class="contentValue"><label><b>North American</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Document Creator : </div><div class="contentValue"><label><b> jhon Smith</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Approver : </div><div class="contentValue"><label><b> Alex Liu</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Date of Approval : </div><div class="contentValue"><label><b>21/05/2013</b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Violated Test : </div><div class="contentValue"><label><b> Yes </b></label></div></div>
                 <div class="detailContent"><div class="contentLabel">Intended Payee : </div><div class="contentValue"><label><b> Rixford Brett </b></label></div></div>
             </div>
         </div>
     </div>-->
    <div id="tabs-2">

        <div id="continueRiskAssessmentTransactionSummaryDiv">
            <br/>
            <br/>
            <table id="summeryTable2" class="tablesorter2">
                <thead>
                <tr>
                    <th>Transaction ID</th>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Region</th>
                    <th>Document Creator</th>
                    <th>Approver</th>
                    <th>Date of Approval</th>
                    <th>Decission</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>1/1/13</td>
                    <td>$10,000</td>
                    <td>North American</td>
                    <td>John Smith</td>
                    <td>Alex Liu</td>
                    <td>2/1/13</td>
                    <td>Irrelevant/Non Responsive</td>

                </tr>
                <tr>
                    <td>2</td>
                    <td>1/10/13</td>
                    <td>$20,000</td>
                    <td>South American</td>
                    <td>Roger Smith</td>
                    <td>Alex Liu</td>
                    <td>1/20/13</td>
                    <td>Further Review Required</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>1/11/13</td>
                    <td>$100,000</td>
                    <td>Asian</td>
                    <td>Bond Roni</td>
                    <td>Alex Liu</td>
                    <td>2/11/13</td>
                    <td>Additional Information Required</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>1/26/13</td>
                    <td>$15,000</td>
                    <td>North American</td>
                    <td>Andrew Ross</td>
                    <td>John Smith</td>
                    <td>1/27/13</td>
                    <td>Further Review Required</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>1/1/13</td>
                    <td>$10,000</td>
                    <td>North American</td>
                    <td>John Smith</td>
                    <td>Alex Liu</td>
                    <td>2/1/13</td>
                    <td>Irrelevant/Non Responsive</td>
                </tr>
                <tr>
                    <td>6</td>
                    <td>1/10/13</td>
                    <td>$20,000</td>
                    <td>South American</td>
                    <td>Roger Smith</td>
                    <td>Alex Liu</td>
                    <td>1/20/13</td>
                    <td>Irrelevant/Non Responsive</td>
                </tr>
                <tr>
                    <td>7</td>
                    <td>1/11/13</td>
                    <td>$100,000</td>
                    <td>Asian</td>
                    <td>Bond Roni</td>
                    <td>Alex Liu</td>
                    <td>2/11/13</td>
                    <td>Irrelevant/Non Responsive</td>
                </tr>
                <tr>
                    <td>8</td>
                    <td>6/11/13</td>
                    <td>$15,000</td>
                    <td>North American</td>
                    <td>Andrew Ross</td>
                    <td>John Smith</td>
                    <td>2/11/13</td>
                    <td>Additional Information Required</td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- Left side Tabs Ends here -->


<div id="tabs1" style="float:left;">
<ul>

    <li><a href="#tabs-3"><b>Transaction Details</b></a></li>
    <!--<li><a href="#tabs-1">Decisions</a></li>-->
    <li><a href="#tabs-2">Controls</a></li>

    <li><a href="#tabs-4">C & D</a></li>
    <!-- <li><a href="#tabs-4">Button block </a></li>	 -->
    <li><a href="#tabs-5">P. History</a></li>
    <li><a href="#tabs-6">A. Trail</a></li>
</ul>
<div class="buttonTab" style="text-align:center">
    <input type="button" id="cancelButton" class="myButton" value="Cancel"/>
    <input type="button" class="myButton" value="Save"/>
    <input type="button" class="myButton" value="Print"/>
    <input type="button" class="myButton" value="Email"/>
</div>
<div id="tabs-3">
    <div id="detailSection" style="width:100%">
        <div class="firstColumn" style="float:left;">
            <div class="detailContent">
                <div class="contentLabel">Transaction ID :</div>
                <div class="contentValue"><label><b>10101</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Date :</div>
                <div class="contentValue"><label><b>21/05/2013</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Amount :</div>
                <div class="contentValue"><label><b>$20,000</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Region :</div>
                <div class="contentValue"><label><b>North American</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Document Creator :</div>
                <div class="contentValue"><label><b> jhon Smith</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Approver :</div>
                <div class="contentValue"><label><b> Alex Liu</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Date of Approval :</div>
                <div class="contentValue"><label><b>21/05/2013</b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Violated Test :</div>
                <div class="contentValue"><label><b> Yes </b></label></div>
            </div>
            <div class="detailContent">
                <div class="contentLabel">Intended Payee :</div>
                <div class="contentValue"><label><b> Rixford Brett </b></label></div>
            </div>
        </div>
    </div>
    <br></br>

    <div class="radioDecissionAreaDiv" style="margin-top: 209px">
        <div class="decisionArea">
            <input type="radio" name="radioDecissionArea" class="radioDecissionArea" value="a"> Irrelevant/Non
            Responsive</input>
        </div>
        <div class="decisionArea">
            <input type="radio" name="radioDecissionArea" class="radioDecissionArea" value="b"> Further Review
            Required</input>
        </div>
        <div class="decisionArea">
            <input type="radio" name="radioDecissionArea" class="radioDecissionArea" value="c" checked="checked">
            Additional Information Required</input>
            <br/><br/><label class="email" id="emailLabel" style=""><b>Email<b></label><input type="text" id="emailText"
                                                                                              style="" class="email"
                                                                                              value="brettrixford@gmail.com"/>
        </div>
    </div>
    <div id="btnNcommentBlock1">
        <textarea class="commentRegion" placeholder="Write a comment..."></textarea>
    </div>
    <!--<span class="btn btn-file">Attach a file   <input class="upFile"type="file" /></span>-->
    <!--<div class="submitComment">
        <button class="myButton">Upload file</button>
    </div>-->
</div>

<!--          <div id="tabs-1" class="tabbedpages">
   <div class="radioDecissionAreaDiv">
       <div class="decisionArea">
           <input type="radio" name="radioDecissionArea" class="radioDecissionArea" value="a">   Irrelevant/Non Responsive</input>
       </div>
       <div class="decisionArea">
           <input type="radio" name="radioDecissionArea" class="radioDecissionArea" value="b">   Further Review Required</input>
       </div>
       <div class="decisionArea">
           <input type="radio" name="radioDecissionArea" class="radioDecissionArea" value="c" checked="checked">   Additional Information Required</input>
           <br/><br/><label class="email" id="emailLabel" style=""><b>Email<b></label><input type="text" id="emailText" style="" class="email" value="brettrixford@gmail.com"/>
       </div>
   </div>

   <div id="btnNcommentBlock1">
       <textarea class="commentRegion" placeholder="Write a comment..."></textarea>
   </div>
&lt;!&ndash;   <div class="submitComment">
                        <button class="myButton">Submit Comment</button>
                    </div>&ndash;&gt;
                </div>-->
<div id="tabs-5" class="tabbedpages">
    <div id="txAccordion" class="ruleHolder1">
        <h3 class="txAccordionHeader"><b>Reviewer Controls</b></h3>

        <div>
            <div class="controlLine">
                <input type="checkbox" class="checkboxCTRLine" checked="checked"/>
                <label>Expenditure is allowable per the Company's policy</label>
            </div>
            <div class="controlLine">
                <input type="checkbox" class="checkboxCTRLine"/>
                <label>Payment/expenditure is not to/on behalf of an individual or government official</label>
            </div>
            <div class="controlLine">
                <input type="checkbox" class="checkboxCTRLine" checked="checked"/>
                <label>At the time payment was made, the vendor is an approved entity per the company approved vendor
                    master file</label>
            </div>
            <div class="controlLine">
                <input type="checkbox" class="checkboxCTRLine"/>
                <label>General ledger account coding appropriately reflects the nature of the transaction</label>
            </div>

            <div class="controlLine">
                <input type="checkbox" class="checkboxCTRLine"/>
                <label>Relevant approvals noted for the transaction in line with policy</label>
            </div>
        </div>
        <h3 class="txAccordionHeader"><b>Reviewer Decision</b></h3>

        <div>
            <div class="radioDecissionAreaDiv">
                <div class="decisionArea">
                    <input type="radio" name="radioDecissionArea1" class="radioDecissionArea" value="a"> Irrelevant/Non
                    Responsive</input>
                </div>
                <div class="decisionArea">
                    <input type="radio" name="radioDecissionArea1" class="radioDecissionArea disabled" value="b">
                    Further Review Required</input>
                </div>
                <div class="decisionArea">
                    <input type="radio" name="radioDecissionArea1" class="radioDecissionArea" checked="checked"
                           value="c"> Additional Information Required</input>
                    <br></br>
                    <label class="email" id="emailLabel" style=""><b>Email</b></label><input type="text" id="emailText"
                                                                                             style="" class="email"
                                                                                             value="brettrixford@gmail.com"></input>
                </div>
            </div>
        </div>
        <h3 class="txAccordionHeader"><b>Comments and Documents</b></h3>

        <div>
            <div class="showComments">
                <div class="userName">Rixford Brett :</div>
                <div class="userAction">
                    <div class="action"><font class="commentText"> Lorem ipsum dolor sit amet, in ut consequat sed
                        viverra volutpat magna. Pharetra sed suscipit lectus</font></div>
                    <div class="date">5th May 05:21am</div>

                </div>
                <div class="userName">Alex Lui :</div>
                <div class="userAction">
                    <div class="action"><img src="images/notepad-icon-.png" class="fileIcon"></img><font
                            class="commentText">Lorem ipsum dolor sit amet, in ut consequat sed viverra volutpat magna.
                        Pharetra sed suscipit lectus</font></div>
                    <div class="date">6th May 08:23 am</div>

                </div>

                <div class="userName">Rixford Brett :</div>
                <div class="userAction">
                    <div class="action"><font class="commentText">Lorem ipsum dolor sit amet, in ut consequat sed
                        viverra volutpat magna. Pharetra sed suscipit lectus</font></div>
                    <div class="date">7th May 10:20 am</div>

                </div>

                <div class="userName">Alex Lui :</div>
                <div class="userAction">
                    <div class="action"><img src="images/zip-file-icon.jpg" class="fileIcon"></img><font
                            class="commentText">Lorem ipsum dolor sit amet, in ut consequat sed viverra volutpat magna.
                        Pharetra sed suscipit lectus</font></div>
                    <div class="date">8th May 09:25 pm</div>

                </div>
                <div class="userName">Rixford Brett :</div>
                <div class="userAction">
                    <div class="action"><font class="commentText">FinalDemo.html file is also needed, please send it to
                        me.</font></div>
                    <div class="date">8th may 05:26 pm</div>

                </div>
                <div class="userName">Alex Lui :</div>
                <div class="userAction">
                    <div class="action"><img src="images/html.png" class="fileIcon"></img><font class="commentText">FinalDemo.html
                        is successfully saved.</font></div>
                    <div class="date">9th May 08:40 pm</div>

                </div>
            </div>
        </div>
    </div>
</div>
<div id="tabs-6" class="tabbedpages">
    <table id="summeryTable3" class="tablesorter">
        <thead>
        <tr>
            <th>Date</th>
            <th>User</th>
            <th>Event</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>12/4/2013</td>
            <td>Jhon Smith</td>
            <td>Checked a decision " Further Review Required"</td>
        </tr>
        <tr>
            <td>12/5/2013</td>
            <td>Karki Helka</td>
            <td>Chacked another control "Expenditure is allowable per the Company's policy"</td>
        </tr>
        <tr>
            <td>13/5/2013</td>
            <td>Rixford Brett</td>
            <td>Attached new file "Demo.rar"</td>
        </tr>
        <tr>
            <td>14/6/2013</td>
            <td>Jhon Smith</td>
            <td>Added new Comment "Please review the new "Demo.rar"</td>
        </tr>
        </tbody>
    </table>
</div>


<div id="tabs-2" class="tabbedpages">
    <div class="controlLine">
        <input type="checkbox" class="checkboxCTRLine" checked="checked"/>
        <label>Expenditure is allowable per the Company's policy</label>
    </div>
    <div class="controlLine">
        <input type="checkbox" class="checkboxCTRLine"/>
        <label>Payment/expenditure is not to/on behalf of an individual or government official</label>
    </div>
    <div class="controlLine">
        <input type="checkbox" class="checkboxCTRLine" checked="checked"/>
        <label>At the time payment was made, the vendor is an approved entity per the company approved vendor master
            file</label>
    </div>
    <div class="controlLine">
        <input type="checkbox" class="checkboxCTRLine"/>
        <label>General ledger account coding appropriately reflects the nature of the transaction</label>
    </div>

    <div class="controlLine">
        <input type="checkbox" class="checkboxCTRLine"/>
        <label>Relevant approvals noted for the transaction in line with policy</label>
    </div>

    <div id="btnNcommentBlock">
        <textarea class="commentRegion" placeholder="Write a comment..."></textarea>

        <div class="btn-group">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                Person Involved
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a tabindex="-1" href="#">Alex Liu</a></li>
                <li><a tabindex="-1" href="#">Rixford Brett</a></li>
                <li><a tabindex="-1" href="#">Lila Kotian</a></li>
                <li><a tabindex="-1" href="#">Jhon Smith</a></li>
            </ul>
        </div>
    </div>
    <br/>
    <br/>
    <br/>
    <!--<div class="submitComment">
                     <button class="myButton">Submit Comment</button>
                 </div>-->
</div>

<!-- 			<div id="tabs-4" class="tabbedpages">
				<div class="buttonTab">
					<input type="button" class="myButton" value="Cancel"/>
					<input type="button" class="myButton" value="Save"/>
					<input type="button" class="myButton" value="Print"/>
					<input type="button" class="myButton" value="Email"/>
				</div>
			</div> -->
<div id="tabs-4" class="tabbedpages">
    <div class="showComments">
        <div class="userName">Rixford Brett :</div>
        <div class="userAction">
            <div class="action"><font class="commentText"> Lorem ipsum dolor sit amet, in ut consequat sed viverra
                volutpat magna. Pharetra sed suscipit lectus</font></div>
            <div class="date">5th May 05:21am</div>

        </div>
        <div class="userName">Alex Lui :</div>
        <div class="userAction">
            <div class="action"><img src="images/notepad-icon-.png" class="fileIcon"></img><font class="commentText">Lorem
                ipsum dolor sit amet, in ut consequat sed viverra volutpat magna. Pharetra sed suscipit lectus</font>
            </div>
            <div class="date">6th May 08:23 am</div>

        </div>

        <div class="userName">Rixford Brett :</div>
        <div class="userAction">
            <div class="action"><font class="commentText">Lorem ipsum dolor sit amet, in ut consequat sed viverra
                volutpat magna. Pharetra sed suscipit lectus</font></div>
            <div class="date">7th May 10:20 am</div>

        </div>

        <div class="userName">Alex Lui :</div>
        <div class="userAction">
            <div class="action"><img src="images/zip-file-icon.jpg" class="fileIcon"></img><font class="commentText">Lorem
                ipsum dolor sit amet, in ut consequat sed viverra volutpat magna. Pharetra sed suscipit lectus</font>
            </div>
            <div class="date">8th May 09:25 pm</div>

        </div>
        <div class="userName">Rixford Brett :</div>
        <div class="userAction">
            <div class="action"><font class="commentText">FinalDemo.html file is also needed, please send it to
                me.</font></div>
            <div class="date">8th may 05:26 pm</div>

        </div>
        <div class="userName">Alex Lui :</div>
        <div class="userAction">
            <div class="action"><img src="images/html.png" class="fileIcon"></img><font class="commentText">FinalDemo.html
                is successfully saved.</font></div>
            <div class="date">9th May 08:40 pm</div>

        </div>

        <textarea class="commentRegion" placeholder="Write a comment..."></textarea>
        <br><br>
        <span class="btn btn-file">Attach a file   <input class="upFile" type="file"/></span>

        <div class="submitComment">
            <button class="myButton">Comment and Upload file</button>
        </div>
    </div>
</div>
</div>
</div>


</body>
</html>