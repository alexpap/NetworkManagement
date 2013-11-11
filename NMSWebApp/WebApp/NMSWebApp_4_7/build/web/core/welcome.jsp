<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NMSWebApp</title>
        <style>
            <%@include file="/core/css/style.css" %>
        </style>
    </head>
    <body>
        <div id="page-background">
            <div id="page-background-image"> </div>
        </div>
        <div id="main">
                <div class="sheet">
                    <div class="sheet-tl"></div>
                    <div class="sheet-tr"></div>
                    <div class="sheet-bl"></div>
                    <div class="sheet-br"></div>
                    <div class="sheet-tc"></div>
                    <div class="sheet-bc"></div>
                    <div class="sheet-cl"></div>
                    <div class="sheet-cr"></div>
                    <div class="sheet-cc"></div>
                    <div class="sheet-body">
                    <%@include file="/core/header.jsp" %>

                    <div class="content-layout">
                    <div class="content-layout-row">
                    
                      <div class="layout-cell content">
                        <div class="post">
                         <div class="post-body">
                           <div class="post-inner article">
                            <h2 class="postheader">Welcome</h2>
                            <div class="cleared"></div>
                            <div class="postcontent">
                                <ul>
                                    <li><p> By selecting <a href ="Configure"> Configure</a> you can set up your hosts and the services you'll like to monitor.</p></li>
                                    <li><p> By selecting <a href ="Monitor"> Monitor</a> you can choose your hosts and the services you'll like to monitor.</p></li>

                                </ul>
                                <div class="cleared"></div>
                                
                            </div>
                            <div class="cleared"></div>
                          </div>
                          <div class="cleared"></div>
                        </div>
                      </div>
                      <div class="cleared"></div>
                    </div>
                  </div>
                </div>
                    
                    <div class="cleared reset-box"></div>
                    <%@include file="/core/footer.jsp" %>
                </div>
            </div>
            <div class="cleared"></div>
        </div>
    </body>
</html>
