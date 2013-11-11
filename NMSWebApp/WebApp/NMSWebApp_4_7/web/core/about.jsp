<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NMSWebApp</title>
        <style>
            <%@include file="css/style.css" %>
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
                            <h2 class="postheader">Brief Description</h2>
                            <div class="cleared"></div>
                            <div class="postcontent">
                                <p> 
                                    An NMS manages the network elements, also called managed devices. <br>
                                    Device management includes faults, configuration, accounting, performance, and security (FCAPS) management.<br>
                                    Management tasks include discovering network inventory, monitoring device health and status, 
                                        providing alerts to conditions that impact system performance, and identification of problems, their source(s) and possible solutions. <br>
                                    <br><b>Protocols</b><br>
                                    An NMS employs various protocols to accomplish these tasks.<br>
                                    For example, SNMP protocol can be used to gather the information from devices in the network hierarchy.<br>

                                    <br><b>Network statistics</b><br>
                                    The NMS collects device statistics and may maintain an archive of previous network statistics including problems and solutions that were successful in the past.<br>
                                    If faults recur, the NMS can search the archive for the possible solutions.
                                    <br><a style="float:right;" target="_blank" href ="http://en.wikipedia.org/wiki/Network_management_system" > source wiki </a>
                                </p>
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
