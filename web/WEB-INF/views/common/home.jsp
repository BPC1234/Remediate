<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.dsinv.abac.util.Utils" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%
    final String contextPath = request.getContextPath();

%>
<!-- Header -->
<div class="container">
    <div class="header row">
        <div class="span12">
            <div class="navbar">
                <div class="navbar-inner">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <h1>
                        <a class="brand" href="index.html">BPC Remidiate - Control your risk...</a>

                    </h1>
                    <div class="nav-collapse collapse">
                        <ul class="nav pull-right">
                            <li class="current-page">
                                <a href="home.html"><i class="icon-home"></i><br />Home</a>
                            </li>
                            <li>
                                <a href="feature.html"><i class="icon-comments"></i><br />Feature</a>
                            </li>
                            <li>
                                <a href="support.html"><i class="icon-camera"></i><br />Support</a>
                            </li>
                            <li>
                                <a href="about.html"><i class="icon-user"></i><br />About</a>
                            </li>
                            <li>
                                <a href="contact.html"><i class="icon-envelope-alt"></i><br />Contact</a>
                            </li>
                            <li>
                                <a href="login.html"><i class="icon-lock"></i><br />Login</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Slider -->
<div class="slider">
    <div class="container">
        <div class="row">
            <div class="span10 offset1" style="margin-left: 50px;">
                <div class="flexslider flexsliderNew">
                    <ul class="slides">
                            <li data-thumb="<%= contextPath %>/resources/staticContent/assets/img/slider/11.jpg">
                               <div class="leftDiv">
                                   <img id="leftImage" src="<%= contextPath %>/resources/images/bpcDemo.jpg" />
                               </div>
                               <div class="rightDiv">
                                   <div class="homeButton">sampling methodology</div>
                                   <%--<div class="homeButtonSecond">Mobile to desktop everything looks perfect</div>--%>
                                   <table id="rightDivTable" class="rightDivTable">
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>Elegant and Easy to Use</td></tr>
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>Holiday Recording</td></tr>
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>Concept or thesaurus keyword searching </td></tr>
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>Dashboard,Research dashboard options.</td></tr>
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>fuzzy or phonic searching capabilities</td></tr>
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>Ability to quantify profits for problematic transactions.</td></tr>
                                       <tr><td><i class="icon-ok orangeText"></i></td><td>Ability to extrapolate results (for statistical samples only).</td></tr>
                                   </table>
                                   <div class="homeButtonThird">View Demo</div>
                               </div>

                           </li>
                           <li data-thumb="<%= contextPath %>/resources/staticContent/assets/img/slider/16.jpg">
                               <div class="leftSecondDiv">
                                   <img id="leftSecondImage" src="<%= contextPath %>/resources/staticContent/assets/img/slider/16.jpg" />
                               </div>
                               <div class="rightSecondDiv">
                                   <div class="homeButtonSecond">Pop out standalone detail view</div>
                                   <div class="homeButtonFourth">Context sensitive Help and Help Section</div>
                                   <table class="rightSecondDivTable">
                                       <tr><td><i class="icon-ok cyanText"></i></td><td>able to modify the coding template.</td></tr>
                                       <tr><td><i class="icon-ok cyanText"></i></td><td> When the people fear the government,there is tyranny</td></tr>
                                       <tr><td><i class="icon-ok cyanText"></i></td><td>Bold, Clean Design</td></tr>
                                       <tr><td><i class="icon-ok cyanText"></i></td><td>Modern HTML5 &amp; CSS3 Techniques</td></tr>
                                       <tr><td><i class="icon-ok cyanText"></i></td><td>Clean and Commented Code</td></tr>
                                   </table>
                               </div>
                           </li>
                           <li data-thumb="<%= contextPath %>/resources/staticContent/assets/img/slider/19.jpg">
                               <div class="leftThirdDiv">
                                   <img id="leftThirdImage" src="<%= contextPath %>/resources/staticContent/assets/img/slider/19.jpg" />
                               </div>
                               <div class="rightThirdDiv">
                                   <div class="homeButtonFifth">Pop out detail view</div>
                                   <div class="homeButtonSixth">ability to find similar transactions</div>
                                   <table class="rightThirdDivTable">
                                       <tr><td><i class="icon-ok blueText"></i></td><td>generate reports regarding users progress</td></tr>
                                       <tr><td><i class="icon-ok blueText"></i></td><td>Restrict transactions of a certain type that should be approved through the software.</td></tr>
                                       <tr><td><i class="icon-ok blueText"></i></td><td>Ability to do sample selection for 3rd party review.</td></tr>
                                       <tr><td><i class="icon-ok blueText"></i></td><td>Ability to extrapolate results (for statistical samples only).</td></tr>
                                       <tr><td><i class="icon-ok blueText"></i></td><td>Modern HTML5 &amp; CSS3 Techniques</td></tr>
                                       <tr><td><i class="icon-ok blueText"></i></td><td>Clean and Commented Code</td></tr>
                                   </table>
                               </div>
                           </li>
                        <%--<li data-thumb="<%= contextPath %>/resources/staticContent/assets/img/slider/12.jpg">
                         <img src="<%= contextPath %>/resources/staticContent/assets/img/slider/12.jpg" />
                        </li>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Site Description -->
<div class="presentation container">
    <h2>We are <span class="violet">BPC Remidiate</span>, Control your risk.</h2>
    <p>We design beautiful websites, logos and prints. Your project is safe with us.</p>
</div>

<!-- Services -->
<div class="portfolio what-we-do container" style="margin-bottom:60px;">
    <div class="row">
        <ul class="portfolio-img">
            <li data-id="p-1" data-type="web-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/1_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/1_portfolio.jpg" alt="">
                    </a>
                    <h4>Lorem Website</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
            <li data-id="p-2" data-type="logo-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/9_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/9_portfolio.jpg" alt="">
                    </a>
                    <h4>Ipsum Logo</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
            <li data-id="p-3" data-type="print-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/7_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/7_portfolio.jpg" alt="">
                    </a>
                    <h4>Dolor Prints</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
            <li data-id="p-4" data-type="web-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/6_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/6_portfolio.jpg" alt="">
                    </a>
                    <h4>Sit Amet Website</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
        </ul>
    </div>
</div>

 <script>
     $(document).ready(function () {

         $(".homeButtonThird").click(function(){
            window.location = "login.html";
         });
     });

     function animateTable(firstTime,secondTime){
         $('.rightDivTable').show();
         $('.rightDivTable').css('margin-left','270px');
         $('.rightDivTable').animate({
             marginLeft: '-=260px'
         }, firstTime);

         $('.rightDivTable').animate({
             marginLeft: '+=30px'
         }, secondTime).promise().done(function(){
             animateDemoButton(firstTime,secondTime);
         });


     }

     function animateDemoButton(firstTime,secondTime){
         $('.homeButtonThird').show();
         $('.homeButtonThird').css('margin-top','200px');
         $('.homeButtonThird').animate({
             marginTop: '-=210px'
         }, firstTime);

         $('.homeButtonThird').animate({
             marginTop: '+=30px'
         }, secondTime);
     }

     function animateLeftBPCLogo(firstTime,secondTime){
         $('#leftImage').show();
         $('#leftImage').css('margin-left','-600px');
         $('#leftImage').animate({
             marginLeft: '+=620px'
         }, firstTime);

         $('#leftImage').animate({
             marginLeft: '-=20px'
         }, secondTime).promise().done(function(){
             $('#leftImage').css('margin-left','0px');
         });
     }
     function hideFirstSlide(){
         $('#leftImage').hide();
         $('.homeButtonThird').hide();
         $('.homeButton').hide();
         $('.rightDivTable').hide();
     }
     function startFirstSlideShow(firstTime,secondTime){

         animateLeftBPCLogo(firstTime,secondTime);

         $('.homeButton').show();
         $('.homeButton').css('margin-left','370px');

         $('.homeButton').animate({
             marginLeft: '-=360px'
         }, firstTime);

         $('.homeButton').animate({
             marginLeft: '+=30px'
         }, secondTime).promise().done(function(){
                     animateTable(firstTime,secondTime);
        });
     }

     function startSecondSlideShow(firstTime,secondTime){

         animateLeftSecondLogo(firstTime,secondTime);
         animateSecondDemoButton(firstTime,secondTime);

     }
     function startThirdSlideShow(firstTime,secondTime){

         animateLeftThirdLogo(firstTime,secondTime);
         animateFifthDemoButton(firstTime,secondTime)

     }


     function animateLeftSecondLogo(firstTime,secondTime){
         $('#leftSecondImage').show();
         $('#leftSecondImage').css('margin-top','-200px');

         $('#leftSecondImage').animate({
             marginLeft: '-=300px',
             marginTop: '+=90px'
         }, firstTime).promise().done(function(){
                     $('#leftSecondImage').animate({
                         marginLeft: '+=330px',
                         marginTop: '+=110px'
                     }, firstTime).promise().done(function(){
                                 $('#leftSecondImage').animate({
                                     marginLeft: '-=30px'
                                }, secondTime);
                 });
              });
     }
     function animateLeftThirdLogo(firstTime,secondTime){
         $('#leftThirdImage').show();
         $('#leftThirdImage').css('margin-top','300px');

         $('#leftThirdImage').animate({
             marginLeft: '-=300px',
             marginTop: '-=200px'
         }, firstTime).promise().done(function(){
                     $('#leftThirdImage').animate({
                         marginLeft: '+=350px',
                         marginTop: '-=80px'
                     }, firstTime).promise().done(function(){
                                 $('#leftThirdImage').animate({
                                     marginLeft: '-=50px'
                                 }, secondTime);
                             });
                 });
     }
     function hideSecondSlide(){
         $('#leftSecondImage').hide();
         $('.homeButtonSecond').hide();
         $('.homeButtonFourth').hide();
         $('.rightSecondDivTable').hide();
     }
     function hideThirdSlide(){
         $('#leftThirdImage').hide();
         $('.homeButtonFifth').hide();
         $('.homeButtonSixth').hide();
         $('.rightThirdDivTable').hide();
     }

     function animateSecondDemoButton(firstTime,secondTime){
         $('.homeButtonSecond').show();
         $('.homeButtonSecond').css('margin-top','-200px');
         $('.homeButtonSecond').animate({
             marginTop: '+=260px'
         }, firstTime);

         $('.homeButtonSecond').animate({
             marginTop: '-=30px'
         }, secondTime).promise().done(function(){
                     animateFourthDemoButton(firstTime,secondTime);
      });
     }
     function animateFourthDemoButton(firstTime,secondTime){
         $('.homeButtonFourth').show();
         $('.homeButtonFourth').css('margin-left','270px');
         $('.homeButtonFourth').animate({
             marginLeft: '-=260px'
         }, firstTime);

         $('.homeButtonFourth').animate({
             marginLeft: '+=30px'
         }, secondTime).promise().done(function(){
                     animateSecondTable(firstTime,secondTime);
                 });
     }
     function animateSecondTable(firstTime,secondTime){
         $('.rightSecondDivTable').show();
         $('.rightSecondDivTable').css('margin-left','270px');
         $('.rightSecondDivTable').animate({
             marginLeft: '-=260px'
         }, firstTime);

         $('.rightSecondDivTable').animate({
             marginLeft: '+=30px'
         }, secondTime);
     }

     function animateFifthDemoButton(firstTime,secondTime){
         $('.homeButtonFifth').show();
         $('.homeButtonFifth').css('margin-left','270px');
         $('.homeButtonFifth').animate({
             marginLeft: '-=260px'
         }, firstTime);

         $('.homeButtonFifth').animate({
             marginLeft: '+=30px'
         }, secondTime).promise().done(function(){
                     animateSixthDemoButton(firstTime,secondTime);
                 });
     }

     function animateSixthDemoButton(firstTime,secondTime){
         $('.homeButtonSixth').show();
         $('.homeButtonSixth').css('margin-top','-200px');
         $('.homeButtonSixth').animate({
             marginTop: '+=260px'
         }, firstTime);

         $('.homeButtonSixth').animate({
             marginTop: '-=30px'
         }, secondTime).promise().done(function(){
                     animateThirdTable(firstTime,secondTime);
                 });
     }

     function animateThirdTable(firstTime,secondTime){
         $('.rightThirdDivTable').show();
         $('.rightThirdDivTable').css('margin-left','270px');
         $('.rightThirdDivTable').animate({
             marginLeft: '-=260px'
         }, firstTime);

         $('.rightThirdDivTable').animate({
             marginLeft: '+=30px'
         }, secondTime);
     }

 </script>
