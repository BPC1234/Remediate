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
                            <li>
                                <a href="home.html"><i class="icon-home"></i><br />Home</a>
                            </li>
                            <li>
                                <a href="feature.html"><i class="icon-comments"></i><br />Feature</a>
                            </li>
                            <li class="current-page">
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
<!-- Page Title -->
<div class="page-title">
    <div class="container">
        <div class="row">
            <div class="span12">
                <i class="icon-tasks page-title-icon"></i>
                <h2>Support /</h2>
                <p>Here are the services we offer</p>
            </div>
        </div>
    </div>
</div>
<footer class="marginTop shadowTop">
    <div class="container" style="margin-left: 84px;">
        <div class="row">
            <div class="widget span3 supportBannerDiv">
                <h4>White Papers</h4>
                    <p><i class="icon-map-marker cyanText"></i><a href="">Legal Official documents</a></p>
                    <p><i class="icon-map-marker cyanText"></i><a href="">Declarations</a> </p>
                    <p><i class="icon-map-marker cyanText"></i><a href="">Lorem ipsum dolor sit amet, consectetur </a></p>
                    <p><i class="icon-map-marker cyanText"></i><a href="">consectetur adipisicing elit</a></p>
               <%-- <div class="call-to-action-text" style="padding:0px;width: 124px; float:right;">
                    <div class="ca-button more">
                        <a href="login.html">Try It Now!</a>
                    </div>
                </div>--%>
            </div>
            <div class="widget span3 supportBannerDiv">
                <h4>Video Tutorial</h4>
                <div>
                <a href="http://103.4.147.139/video/abac/ABAC_Demo.mp4" target="_blank">
                    <img src="<%= contextPath %>/resources/staticContent/assets/img/123.png">
                </a>
                <%--<video height="130" controls>
                    <source src="http://103.4.147.139/video/abac/ABAC_Demo.mp4" type="video/mp4">
                </video>--%>
                </div>

            </div>
            <div class="widget span3 supportBannerDiv">
                <h4>Screen Shots</h4>
                <ul class="flickr-feed">
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p1.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p1.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p2.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p2.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p3.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p3.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p4.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p4.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p5.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p5.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p6.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p6.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p7.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p7.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p8.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p8.png"></a></li>
                    <li><a target="_blank" href="<%= contextPath %>/resources/staticContent/assets/img/slider/p9.png"><img
                            alt="Melbourne Meetup"
                            src="<%= contextPath %>/resources/staticContent/assets/img/slider/p9.png"></a></li>
                </ul>
            </div>
            <div class="widget span3">
                <h4>FAQ</h4>
                <p><i class="icon-user lightGreenText"></i><a href=""> Purpose of this application ?</a></p>
                <p><i class="icon-user lightGreenText"></i><a href=""> How to operate transaction monitoring ? </a></p>
                <p><i class="icon-user lightGreenText"></i><a href=""> Percentage of accuracy ?</a></p>
                <p><i class="icon-user lightGreenText"></i><a href=""> Responsiveness ?</a></p>
            </div>
        </div>
    </div>
</footer>
<!-- Services Full Width Text -->
<div class="services-full-width container">
    <div class="row">
        <div class="services-full-width-text span12 " style="height:80px ">

        </div>
    </div>
</div>
