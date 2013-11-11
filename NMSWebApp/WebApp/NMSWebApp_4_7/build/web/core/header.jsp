<%@page import="db.User"%>
<div class="header">
    <div class="header-clip">
        <div class="header-center">
            <div class="header-png"></div>
            <div class="header-jpeg"></div>
        </div>
    </div>
    <div class="logo">
    <h1 class="logo-name"><a href="Home">Network Management System</a></h1>
  </div>
</div>
<div class="cleared reset-box"></div>
<div class="nav">
        <div class="nav-l"></div>
        <div class="nav-r"></div>
  <div class="nav-outer">
          <ul class="hmenu">
                  <li><a href="Home"><span class="l"></span><span class="r"></span><span class="t">Home</span></a></li>	
                  <li><a href="About"><span class="l"></span><span class="r"></span><span class="t">About</span></a></li>	
                  <li><a href="Configure"><span class="l"></span><span class="r"></span><span class="t">Configure</span></a></li>	
                  <li><a href="Monitor"><span class="l"></span><span class="r"></span><span class="t">Monitor</span></a></li>
                  <% 
                    User user = (User)session.getAttribute("user");
                    if (user != null) { %>
                  <li><a href="Logout"><span class="l"></span><span class="r"></span><span class="t">Logout</span></a></li>	
                  <%}%>
          </ul>
  </div>
</div>
<div class="cleared reset-box"></div>