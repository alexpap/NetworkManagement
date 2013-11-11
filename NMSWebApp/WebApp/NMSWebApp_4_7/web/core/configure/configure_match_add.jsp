<%@page import="db.Service"%>
<%@page import="db.Host"%>
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
                                    <h2 class="postheader">Match overview</h2>
                                    <div class="cleared"></div>
                                    <div class="postcontent">
                                      <ul><form action="ConfigureMatch" method="post">
                                          <% 
                                              Host host = (Host)request.getAttribute("host");
                                              Service serv = (Service)request.getAttribute("service");
                                          %>
                                        <li>
                                            <table>
                                                <tr><td>Host's Name: </td>
                                                        <td> <%= host.getName()%> </td></tr>
                                                <tr><td>Host's Ip: </td>
                                                    <td> <%= host.getIp() %></td></tr>
                                                <tr><td>Service's Name:  </td>
                                                    <td> <%= serv.getName() %> </td></tr>
                                                <tr><td>Service's Oid:  </td>
                                                    <td> <%= serv.getOid() %> </td></tr>
                                                <tr><td>Service's Type:  </td>
                                                    <td>  <%= serv.getType() %> </td></tr>
                                                <tr><td>Service's Threshold Value:  </td>
                                                <td><input  name="thd_value" type="text" size="30" value="" /></td></tr> 
                                                <tr><td>Service's Timeout Value:  </td>
                                                <td><input  name="timeout_value" type="text" size="30" value="" /></td></tr> 
                                            </table></li> 
                                            <input  hidden name="host_id" type="text" value=<%=host.getId() %>   />
                                            <input  hidden name="service_id" type="text" value=<%=serv.getId() %>   /> 
                                        <input name="hostservice_added" type="submit" value="Apply"/>
                                      </form>
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

