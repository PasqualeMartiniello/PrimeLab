<%-- 
    Document   : newPrediction
    Created on : 16-feb-2017, 11.28.33
    Author     : pasqualemartiniello
--%>
<div class="row">   
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content" style="display: block;">
                <div class="tabbable-custom">
                    <ul class="nav nav-tabs" name="section">
                        <li class="active" id="section1">
                            <a href="#summary" data-toggle="tab" aria-expanded="true" class=""> Summary </a>
                        </li>
                        <li class="" id="section2">
                            <a href="#predictors" data-toggle="tab" aria-expanded="false" class=""> Predictors </a>
                        </li>
                    </ul>
                    <div id="myTabContent2" class="tab-content">
                         <div class="tab-pane active in fade" id="summary">
                            <div class="x_title">
                                <h3>Model evalutation chart</h3>
                            </div>
                            <div class="x_content">
                                <div id="graph" style="width:100%; height:300px;"></div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="predictors">
                            <div class="x_title">
                                <h3>Predictors</h3>
                            </div>
                            <div class="x_content col-md-12">
                                <div class="table-responsive">
                                    <table id="datatable" class="table nowrap table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Class name</th>
                                                <th>LOC</th>
                                                <th>CBO</th>                                            
                                                <th>LCOM</th>                                         
                                                <th>NOM</th>
                                                <th>RFC</th>
                                                <th>WMC</th>
                                                <th>changes</th>
                                                <th>FIChanges</th>
                                                <th>structuralScatteringSum</th>
                                                <th>semanticScatteringSum</th>
                                                <th>developers</th>
                                                <th>Predicted</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>