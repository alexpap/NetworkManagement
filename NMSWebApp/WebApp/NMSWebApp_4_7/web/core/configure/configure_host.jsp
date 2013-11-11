<%@page import="java.util.Map"%>
<%@page import="java.util.Hashtable"%>
<%@page import="db.Host"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
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
                            <div class="post-inner article" id="serv" >
                                <h2 class="postheader">Host configuration</h2>
                                <div class="cleared"></div>
                                <div class="postcontent">
                                    <ul>
                                        <%
                                        Hashtable<Integer,Host> hosts = (Hashtable<Integer,Host>)request.getAttribute("hosts");
                                        if (!hosts.isEmpty()){
                                        %>
                                        <h3>Set up Hosts</h3>
                                    <li><table>    
                                          <tr> 
                                              <th>Name </th> 
                                              <th>Ip </th> 
                                              <th>Port </th>
                                              <th>Trap port</th>
                                          </tr>
                                          <% 
                                                Iterator<Map.Entry<Integer,Host>> it1 = hosts.entrySet().iterator();
                                                while ( it1.hasNext() ) {
                                                    Map.Entry<Integer,Host> entry = it1.next();
                                                    Host host = entry.getValue();                                                                                                   
                                          %>
                                          <tr> 
                                            <td><%= host.getName() %>          </td> 
                                            <td><%= host.getIp() %>          </td> 
                                            <td><%= host.getPort() %>          </td> 
                                            <td><%= host.getTrap_port() %>          </td> 
                                            <td>
                                                <div style="display: inline-block">
                                                    <form style="display: inline-block" action="ConfigureHost" method="post">
                                                        <input hidden type="text" name="host_id" value=<%= host.getId()  %> />
                                                        <input name="host_edit" type="submit" value="Edit">
                                                    </form>
                                                        <form style="display: inline-block" action="ConfigureHost" method="post">
                                                        <input hidden type="text" name="host_id" value=<%=host.getId()  %> />                                                        
                                                        <input name="host_remove" type="submit" value="Remove">  
                                                    </form>
                                                </div>
                                            </td> 
                                           </tr>                                               
                                           <% }  
                                        }else{  %>   
                                        <h3>Please start by adding a host.</h3>
                                        <%} %>
                                      </table></li>
                                      <li><form action="ConfigureHost" method="post"><input name="host_add" type="submit" value="Add New Host" ></form></li>
                                    </ul>     
                                </div>
                                <div class="cleared"></div>
                            </div>
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

