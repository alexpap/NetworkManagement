<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NMSWebApp</title>
        <style>
            <%@include file="/core/css/style.css" %>
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
         
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
                    <div class="layout-cell sidebar1">
                        <%@include file="/core/configure/configure_sidebar.html" %>
                        <div class="block">
                            <div class="block-body">
                                <div class="blockheader">
                                </div>
                                <div class="blockcontent">
                                    <div class="blockcontent-body">
                                        <div class="cleared"></div>
                                    </div>
                                </div>
                                <div class="cleared"></div>
                            </div>
                        </div>
                        <div class="cleared"></div>
                    </div>
                    <div class="layout-cell content">
                    <div class="post">
                        <div class="post-body">
                            <div class="post-inner article" id="sys" >
                                <h2 class="postheader">Configuration</h2>
                                <div class="cleared"></div>
                                <div class="postcontent">
                                    <ul>
                                        <li><h3><a href="ConfigureSettings"> General Settings:</a></h3></li>
                                        <li>With this option you can Add or Remove users and change Database </li> <br>
                                        <li><h3><a href="ConfigureService"> Set up Services:</a></h3></li>
                                        <li>With this option you can Add, Edit or Remove services from the available services list</li> <br>
                                        <li><h3><a href="ConfigureHost">Set up Hosts:</a> </h3></li>
                                        <li>With this option you can Add, Edit or Remove hosts/machines from the available hosts list</li> <br>
                                        <li><h3><a href="ConfigureMatch">Match Service/Host:</a></h3></li>
                                        <li>With this option you can Add or Remove services to each host OR host to each service</li> <br>
                                    </ul>     
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
      <div class="cleared"></div>
      <%@include file="/core/footer.jsp" %>

    </div>
    </div>
    <div class="cleared"></div>
    </div>
    </body>
</html>
