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
                                    <h2 class="postheader">Host Edit</h2>
                                    <div class="cleared"></div>
                                    <div class="postcontent">
                                      <ul>
                                        <form action="ConfigureHost" method="post">
                                          <% 
                                              Host host = (Host)request.getAttribute("host_to_be_edit");
                                          %>
                                          <input  name="host_id" type="text" value=<%= host.getId() %>  hidden />
                                            <table>
                                              <tr>
                                                  <td>Name: </td>
                                                  <td><input  name="host_name" type="text" value="<%= host.getName()%>" /></td>
                                              </tr>
                                              <tr>
                                                  <td>Ip: </td>
                                                  <td><input  name="host_ip" type="text" value="<%= host.getIp()%>" /></td>
                                              </tr>
                                              <tr>
                                                  <td>Port: </td>
                                                  <td><input  name="host_port" type="text" value="<%= host.getPort()%>" /></td>
                                              </tr>
                                              <tr>
                                                  <td>Trap Port: </td>
                                                  <td><input  name="host_trap_port" type="text" value="<%= host.getTrap_port()%>" /></td>
                                              </tr>
                                            </table>
                                          <input name="host_edited" type="submit" value="Apply"/>
                                          <% 
                                            String err = null;
                                            if ( (err=(String)request.getAttribute("error")) != null ) {
                                        %>
                                        <li><p style="color: red;"><%=err%></p></li>
                                        <% } %>
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
