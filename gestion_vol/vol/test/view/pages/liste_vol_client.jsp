<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*, src.models.*" %>

<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>MaterailM Free Bootstrap Admin Template by WrapPixel</title>
  <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/assets/images/logos/favicon.png" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.min.css" />
</head>

<body>
  <!--  Body Wrapper -->
  <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
    data-sidebar-position="fixed" data-header-position="fixed">
    <!-- Sidebar Start -->
    <aside class="left-sidebar">
      <!-- Sidebar scroll-->
      <div>
        <div class="brand-logo d-flex align-items-center justify-content-between">
          <a href="./index.html" class="text-nowrap logo-img">
            <img src="${pageContext.request.contextPath}/assets/images/logos/logo.svg" alt="" />
          </a>
          <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
            <i class="ti ti-x fs-8"></i>
          </div>
        </div>
        <!-- Sidebar navigation-->
        <%@ include file="sidebar_client.jsp" %>

        <!-- End Sidebar navigation -->
      </div>
      <!-- End Sidebar scroll-->
    </aside>
    <!--  Sidebar End -->
    <!--  Main wrapper -->
    <div class="body-wrapper">
      <!--  Header Start -->
      <%@ include file="header.jsp" %>
      <!--  Header End -->

      <% 
      List<Vol> vols = (List<Vol>) request.getAttribute("vols"); 
  
      // Vérification si la liste d'avions n'est pas nulle ou vide
      if (vols != null && !vols.isEmpty()) { 
    %>
      <div class="body-wrapper-inner">
        <div class="container-fluid">
        
            <div class="card">
            <% for (Vol v : vols) {  %>

            <div class="card-body">
              <div class="row">
                <div class="col-md-4">
                  <h5 class="card-title fw-semibold mb-4">Vol0000<%= v.getId() %></h5>
                  <div class="card">
                    <img src="${pageContext.request.contextPath}/assets/images/products/vol_logo.jpg" class="card-img-top" alt="...">
                    <div class="card-body">
                      <h5 class="card-title"><%= v.getIdAvion().getNom() %> : <%= v.getEtat() %></h5>
                      <p class="card-text">Heure de depart : <%= v.getDateHeureDepart() %></p>
                      <p class="card-text">Heure d'arrivee : <%= v.getDateHeureArrivee() %></p>
                      <p class="card-text">Ville de depart : <%= v.getVilleDepart().getNom() %></p>
                      <p class="card-text">Ville d'arrivee : <%= v.getVilleArrivee().getNom() %></p>

                    </div>
                  </div>
                </div>
              </div>
            </div>
        

          <%  
        }  
        
      }  
      else { %>
        <p>Aucun vol disponible.</p>
    <% }  %>
        </div>
        </div>
      </div>
    </div>
  </div>
  <script src="${pageContext.request.contextPath}assets/libs/jquery/dist/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}assets/js/sidebarmenu.js"></script>
  <script src="${pageContext.request.contextPath}assets/js/app.min.js"></script>
  <script src="${pageContext.request.contextPath}assets/libs/simplebar/dist/simplebar.js"></script>

  
  <!-- solar icons -->
  <script src="https://cdn.jsdelivr.net/npm/iconify-icon@1.0.8/dist/iconify-icon.min.js"></script>
</body>

</html>