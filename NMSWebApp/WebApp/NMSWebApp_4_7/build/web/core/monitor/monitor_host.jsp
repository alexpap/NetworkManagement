<%@page import="db.Service"%>
<%@page import="db.HostService"%>
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

<div class="vmenublock">
                <div class="vmenublock-body">
                  <div class="vmenublockheader">
                      <h3 class="t" id="sys">System Monitor</h3>
                  </div>
                  <%@include file="/core/monitor/monitor_sidebar.html" %>
                </div>
              </div>                        <div class="block">
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
                                <h2 class="postheader">Match configuration</h2>
                                <div class="cleared"></div>
                                <div class="postcontent">
                                    <h3>Match Hosts with Services</h3>
                                    <ul>
                                        <li>
                                          <table>
                                            <tr>
                                            <th>Host Name </th>
                                            <th>Service Name </th>
                                            <th>Type </th>
                                            <th>Action </th>
                                            </tr>
                                     <% 
                                     Hashtable<Integer,Host> hosts = (Hashtable<Integer,Host>)request.getAttribute("hosts");
                                     Hashtable<Integer,Service> services = (Hashtable<Integer,Service>)request.getAttribute("services");
                     		     Hashtable<Integer,HostService> hostservices = (Hashtable<Integer,HostService>)request.getAttribute("hostservices");
                                     Iterator<Map.Entry<Integer,HostService>> it1 = hostservices.entrySet().iterator();
                                     while ( it1.hasNext() ) {
                                       Map.Entry<Integer,HostService> entry = it1.next();
                                       HostService hh = entry.getValue(); 
                                       Host h = hosts.get(hh.getHost().getId());
                                       Service s = services.get(hh.getService().getId());
                                     %>
                                         
                                         <tr> 
                                           <td><%= h.getName() %>   </td> 
                                           <td><%= s.getName() %>   </td>
                                           <td><%= s.getType() %>   </td>
                                           <td>
                                               <form method="post" action="MonitorHost" >
                                                   <input hidden name="hostservice_id" type="text" value=<%=hh.getId() %> />
                                                   <input name="hostservice_monitor" type="submit" value="Monitor"/>
                                               </form>
                                           </td>                                          
                                      </tr>                                               
                                      <%
                                      }                                                                                                   
                                      %>
                                    </table>
                                  </li>
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
