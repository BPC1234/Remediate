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
                            <li class="current-page">
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
<!-- Page Title -->
<div class="page-title">
    <div class="container">
        <div class="row">
            <div class="span12">
                <i class="icon-tasks page-title-icon"></i>
                <h2>Feature /</h2>
                <p>Here are the services we offer</p>
            </div>
        </div>
    </div>
</div>

<!-- Services -->
<div class="portfolio portfolio-page container">
    <div class="row">
        <div class="portfolio-navigator span12">
            <h4 class="filter-portfolio">
                <a href="#" id="active-imgs" class="all">All</a> /
                <a href="#" id="" class="web-design">Web Design</a> /
                <a href="#" id="" class="logo-design">Logo Design</a> /
                <a href="#" id="" class="print-design">Print Design</a>
            </h4>
        </div>
    </div>
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
            <li data-id="p-5" data-type="logo-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/5_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/5_portfolio.jpg" alt="">
                    </a>
                    <h4>Consectetur Logo</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
            <li data-id="p-6" data-type="print-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/6_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/6_portfolio.jpg" alt="">
                    </a>
                    <h4>Adipisicing Print</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
            <li data-id="p-7" data-type="web-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/3_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/3_portfolio.jpg" alt="">
                    </a>
                    <h4>Elit Website</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
            <li data-id="p-8" data-type="print-design" class="span3">
                <div class="work">
                    <a href="<%= contextPath %>/resources/staticContent/assets/img/portfolio/8_portfolio.jpg" rel="prettyPhoto">
                        <img src="<%= contextPath %>/resources/staticContent/assets/img/portfolio/8_portfolio.jpg" alt="">
                    </a>
                    <h4>Sed Do Prints</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
                </div>
            </li>
        </ul>
    </div>
</div>
<!-- Services Half Width Text -->
<div class="services-half-width container">
    <div class="row">
        <div class="services-half-width-text span6">
            <h4>Lorem Ipsum</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper <span class="violet">suscipit lobortis</span> nisl ut aliquip ex ea commodo consequat. Lorem ipsum <strong>dolor sit amet</strong>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do <strong>eiusmod tempor</strong> incididunt.</p>
        </div>
        <div class="services-half-width-text span6">
            <h4>Dolor Sit Amet</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper <span class="violet">suscipit lobortis</span> nisl ut aliquip ex ea commodo consequat. Lorem ipsum <strong>dolor sit amet</strong>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do <strong>eiusmod tempor</strong> incididunt.</p>
        </div>
    </div>
</div>

<!-- Call To Action -->
<div class="call-to-action container">
    <div class="row">
        <div class="call-to-action-text span12">
            <div class="ca-text">
                <p>Lorem ipsum <span class="violet">dolor sit amet</span>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et ut wisi enim.</p>
            </div>
            <div class="ca-button">
                <a href="login.html">Try It Now!</a>
            </div>
        </div>
    </div>
</div>

