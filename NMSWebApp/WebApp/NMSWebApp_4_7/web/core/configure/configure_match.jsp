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
                                <h2 class="postheader">Match configuration</h2>
                                <div class="cleared"></div>
                                <div class="postcontent">
                                    <ul>
                                        <%
                                        Hashtable<Integer,Host> hosts = (Hashtable<Integer,Host>)request.getAttribute("hosts");
                                        Hashtable<Integer,Service> services = (Hashtable<Integer,Service>)request.getAttribute("services");
                                        if (!hosts.isEmpty() && !services.isEmpty()){
                                        %>
                                    <h3>Please match the Hosts with the Services you want.</h3>
                                     <% 
                     		     Hashtable<Integer,HostService> hostservices = (Hashtable<Integer,HostService>)request.getAttribute("hostservices");
                                     Iterator<Map.Entry<Integer,Host>> it1 = hosts.entrySet().iterator();
                                     while ( it1.hasNext() ) {
                                       Map.Entry<Integer,Host> entry = it1.next();
                                       Host host = entry.getValue(); 
                                     %>
                                      <li>
                                          <p><h5>Host: <%= host.getName() %>  with ip: <%= host.getIp() %> :</h5></p>       
                                        <table>
                                         <tr><th>Name </th><th>Description </th><th>OID </th><th>Type</th><th>Action </th></tr>
                                         <% 
                                          Iterator<Map.Entry<Integer,Service>> it2 = services.entrySet().iterator();
                                          while ( it2.hasNext() ) {
                                              Map.Entry<Integer,Service> entry2 = it2.next();
                                              Service service = entry2.getValue();
                                         %>
                                        <tr> 
                                           <td><%= service.getName() %>   </td> 
                                           <td><%= service.getDesc() %>   </td>
                                           <td><%= service.getOid()%>     </td>
                                           <td><%= service.getType()%>    </td>
                                           <td>
                                              <div style="display: inline-block">
                                               <%  
                                               boolean val = false;
                                               HostService hostservice = new HostService();
                                               Iterator<Map.Entry<Integer,HostService>> it3 = hostservices.entrySet().iterator();
                                               while ( it3.hasNext() ) {
                                                 Map.Entry<Integer,HostService> entry3 = it3.next();
                                                 hostservice = entry3.getValue();
                                                 if ( (host.getId() == hostservice.getHost().getId()) && (service.getId() == hostservice.getService().getId()) ) {
                                                     val = true;
                                                     break;
                                                 }
                                               }
                                               if (val){
                                               %>
                                                <form style="display: inline-block" action="ConfigureMatch" method="post">
                                                    <input hidden type="text" name="host_id" value=<%= host.getId()  %> /> 
                                                   <input hidden type="text" name="service_id" value=<%= service.getId()  %> /> 
                                                    <input hidden type="text" name="hostservice_id" value=<%= hostservice.getId() %> />                                                        
                                                    <% if (!hostservice.isActive()){   %>
                                                    <input name="hostservice_remove" type="submit" value="Remove"/>
                                                    <input name="hostservice_edit" type="submit" value="Edit" />                                                    
                                                    <input name="hostservice_start" type="submit" value="Start">
                                                    <%} else {%>
                                                    <input name="hostservice_stop" type="submit" value="Stop">
                                                    <%  }%>
                                                </form>
                                               <% } else{  %>
                                                   <form style="display: inline-block" action="ConfigureMatch" method="post">
                                                   <input hidden type="text" name="host_id" value=<%= host.getId()  %> /> 
                                                   <input hidden type="text" name="service_id" value=<%= service.getId()  %> />                                                        
                                                   <input name="hostservice_add" type="submit" value="Add"/>  
                                                 </form>
                                               <% 
                                                  }
                                               %>
                                             </div>
                                            </td> 
                                      </tr>                                               
                                      <%
                                      }  %>
                                    </table>
                                  </li>
                                  <li><div style="display: inline-block">
                                    <form style="display: inline-block" action="ConfigureMatch" method="post">
                                        <input name="hostservice_startall" type="submit" value="Start All">
                                        <input hidden type="text" name="host_id" value=<%= host.getId()  %> /> 
                                        <input name="hostservice_stopall" type="submit" value="Stop All">
                                    </form>                
                                 </div></li>
                                 <%
                                 }        
                                 }else{
                                      if (hosts.isEmpty()){ %>
                                         <h3>Please start by adding a host.</h3>
                                         <li>Please go to the <a href="ConfigureHost">Set Up Hosts page</a> to add new host.</li>
                                  <% }
                                     if (services.isEmpty()){%>
                                        <h3>Please start by adding a service.</h3>                                                    
                                        <li>Please go to the <a href="ConfigureService">Set Up Services page</a> to add new service.</li>
                               <%} }  %>
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
