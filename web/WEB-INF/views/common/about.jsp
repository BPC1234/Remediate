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
                                <a href="index.html"><i class="icon-home"></i><br />Home</a>
                            </li>
                            <li>
                                <a href="feature.html"><i class="icon-comments"></i><br />Feature</a>
                            </li>
                            <li>
                                <a href="support.html"><i class="icon-camera"></i><br />Support</a>
                            </li>
                            <li class="current-page">
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
                <i class="icon-user page-title-icon"></i>
                <h2>About Us /</h2>
                <p>Below you can find more information about our company</p>
            </div>
        </div>
    </div>
</div>

<div class="team container marBottom">
    <div class="team-title">
        <h3>Meet Our Team</h3>
    </div>
    <div class="row">
        <div class="team-text span3">
            <img alt="" src="<%= contextPath %>/resources/staticContent/assets/img/team/1.jpg">
            <h4>Jamal Ahmad</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...</p>
            <div class="social-links">
                <a href="" class="facebook"></a>
                <a href="" class="twitter"></a>
                <a href="" class="linkedin"></a>
                <a href="" class="email"></a>
            </div>
        </div>
        <div class="team-text span3">
            <img alt="" src="<%= contextPath %>/resources/staticContent/assets/img/team/m3.jpg">
            <h4>Vijay</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...</p>
            <div class="social-links">
                <a href="" class="facebook"></a>
                <a href="" class="twitter"></a>
                <a href="" class="linkedin"></a>
                <a href="" class="email"></a>
            </div>
        </div>
        <div class="team-text span3">
            <img alt="" src="<%= contextPath %>/resources/staticContent/assets/img/team/m4.jpg">
            <h4>George Georgiades</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod...</p>
            <div class="social-links">
                <a href="" class="facebook"></a>
                <a href="" class="twitter"></a>
                <a href="" class="linkedin"></a>
                <a href="" class="email"></a>
            </div>
        </div>
        <div class="team-text span3">
            <img alt="" src="<%= contextPath %>/resources/staticContent/assets/img/team/2.jpg">
            <h4>Shermeen Siddiqui</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod...</p>
            <div class="social-links">
                <a href="" class="facebook"></a>
                <a href="" class="twitter"></a>
                <a href="" class="linkedin"></a>
                <a href="" class="email"></a>
            </div>
        </div>
    </div>
</div>
<!-- About Us Text -->
<div class="about-us container marBottom">
    <div class="row">
        <div class="about-us-text span12">
            <h4>About BPC</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper <span class="violet">suscipit lobortis</span> nisl ut aliquip ex ea commodo consequat. Lorem ipsum <strong>dolor sit amet</strong>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do <strong>eiusmod tempor</strong> incididunt.</p>
            <h4>Our Mission</h4>
            <p>Lorem ipsum dolor sit amet, <span class="violet">consectetur adipisicing</span> elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, <strong>consectetur adipisicing</strong> elit, sed do eiusmod <span class="violet">tempor incididunt</span> ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.</p>
            <h4>Why Choose Us</h4>
            <p>Lorem ipsum dolor sit amet, <strong>consectetur adipisicing elit</strong>, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et. Ut wisi enim ad minim veniam, quis <span class="violet">nostrud exerci</span> tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.</p>
        </div>
    </div>
</div>