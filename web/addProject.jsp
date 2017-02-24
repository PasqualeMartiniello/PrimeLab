<%-- 
    Document   : addProject
    Created on : 12-gen-2017, 9.08.33
    Author     : pasqualemartiniello
--%>
<%@page import="java.util.ArrayList"%>


<jsp:include page="header.jsp" />
<!-- top navigation -->
<!--<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <label class="breadcrumb"><a href="#"> Pagina 1 </a> | <a href="#"> Pagina 2 </a> | Pagina 3 </label>

                </li>
            </ul>
        </nav>
    </div>
</div>-->
<!-- /top navigation -->
<!-- page content -->
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>Add Project</h3>
            </div>

            <div class="title_right">

            </div>
        </div>

        <div class="clearfix"></div>


    </div>
    <!-- CONTENT -->


    <div class="row ">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add a new Project </h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content" style="display: block;">
                    <br>

                    <form action="AddProjectServlet" method="POST" id="demo-form2" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="">

                        <div class="tabbable-custom">
                            <ul class="nav nav-tabs" name="section">
                                <input type="hidden" name ="type" value="section1"/>
                                <li class="active" id="section1">
                                    <a href="#sectionLink" data-toggle="tab" aria-expanded="false" class=""> Insert a Link</a>
                                </li>
                                <li class="" id="section2">
                                    <a href="#sectionCsv" data-toggle="tab" aria-expanded="false" class=""> Insert a Csv </a>
                                </li>

                            </ul>
                            <div class="tab-content">
                                <div class="active tab-pane" id="sectionLink">
                                    <br>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Github link <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <input type="text" name="github" id="github" class="form-control col-md-7 col-xs-12" required>
                                        </div>
                                    </div>
                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Issue Tracker <span class="required">*</span>
                                        </label>
                                        <div  class="col-md-3 col-sm-6 col-xs-12">
                                            <input id="issueTracker" name="issueTracker" type="text" class="form-control col-md-7 col-xs-12"  required />
                                        </div>
                                    </div>
                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-6 col-xs-12 col-md-offset-3">
                                            <button type="reset" class="btn btn-danger">Cancel</button>
                                            <button type="button" name="confirm" class="btn btn-success">Submit</button>
                                            <div id="modalLink" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                                                <div class="modal-dialog modal-lg">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h4 class="modal-title" id="myModalLabel">Confirm your data</h4>
                                                        </div>
                                                        <div class="modal-body">

                                                            <div class="row">
                                                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Github link: </label>
                                                                <label class="control-label col-md-6 col-sm-6 col-xs-12" id="githubConf">link</label>
                                                            </div>
                                                            <div class="ln_solid"></div>
                                                            <div class="row">
                                                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Issue Tracker link: </label>
                                                                <label class="control-label col-md-6 col-sm-6 col-xs-12" id="issueTrackerConf">link</label>
                                                            </div>
                                                            <div class="ln_solid"></div><div class="ln_solid"></div>
                                                            <div class="row">
                                                                <label class="control-label col-md-12 col-sm-3 col-xs-12">Please enter your e-mail address to be alerted when the results will be available: </label>
                                                            </div>
                                                            <br>
                                                            <div class="row">
                                                                <div class = "col-md-6 col-sm-3 col-xs-12"></div>
                                                                <div class = "col-md-6 col-sm-3 col-xs-12" id="bottomModal">

                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default" onclick="onModalClose()" data-dismiss="modal">Close</button>
                                                            <button type="submit"name="confirm" class="btn btn-success">Confirm</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="sectionCsv">
                                    <br>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Project Name *
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <input type="text" name="projName" id="projName" class="form-control col-md-7 col-xs-12">
                                        </div>
                                    </div> 
                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Csv File *
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <input type="file" name="csv" id="csv" accetp=".csv" class="form-control col-md-7 col-xs-12">
                                        </div>
                                    </div> 
                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-6 col-xs-12 col-md-offset-3">
                                            <button type="reset" class="btn btn-danger">Cancel</button>
                                            <button type="button" name="confirm" class="btn btn-success">Submit</button>
                                            <div id="modalCsv" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                                                <div class="modal-dialog modal-lg">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h4 class="modal-title" id="myModalLabel">Confirm your data</h4>
                                                        </div>
                                                        <div class="modal-body">

                                                            <div class="row">
                                                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Project Name </label>
                                                                <label class="control-label col-md-6 col-sm-6 col-xs-12" id="projNameConf">link</label>
                                                            </div>
                                                            <div class="ln_solid"></div>
                                                            <div class="row">
                                                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Csv </label>
                                                                <label class="control-label col-md-6 col-sm-6 col-xs-12" id="csvConf">link</label>
                                                            </div>
                                                            <div class="ln_solid"></div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" onclick="onModalClose()" class="btn btn-default" data-dismiss="modal">Close</button>
                                                            <button type="submit"name="confirm" class="btn btn-success">Confirm</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <br>
                </div>
            </div>
        </div>
    </div>


    <!-- /CONTENT -->
</div>


<!-- /page content -->

<!-- footer content -->
<jsp:include page="footer.jsp" />

<!-- iCheck -->
<script src="scripts/icheck.js"></script>
<script src="scripts/parsleyjs/dist/parsley.js"></script>

<!-- Parsley -->
<script>

    $('#section1').on('click', function() {
       $('input[name="type"]').val("section1");
       $('input[name="github"]').attr({
           'required' : 'required'
       });
       $('input[name="issueTracker"]').attr({
           'required' : 'required'
       });
       $('input[name="csv"]').removeAttr('required');
       $('input[name="projName"]').removeAttr('required');
    });
    
    $('#section2').on('click', function() {
       $('input[name="type"]').val("section2"); 
        $('input[name="csv"]').attr({
           'required' : 'required'
        });
        $('input[name="projName"]').attr({
           'required' : 'required'
       });
        $('input[name="github"]').removeAttr('required');
        $('input[name="issueTracker"]').removeAttr('required');
    });
    

    isModalOnFocus = false;

    function createModals() {
        if (true === $('#demo-form2').parsley().isValid()) {
            isModalOnFocus = true;
            if($('input[name="type"]').val() === 'section1') {
                document.getElementById("githubConf").innerHTML = $('#github').val();
                document.getElementById("issueTrackerConf").innerHTML = $('#issueTracker').val();
                document.getElementById("bottomModal").innerHTML = '<input type = "email" id = "email" name="email" required="required" class = "form-control" >';
                $('#modalLink').modal();
            } else { 
                document.getElementById("projNameConf").innerHTML = $('#projName').val();
                document.getElementById("csvConf").innerHTML = $('#csv').val();
                $('#modalCsv').modal();
            }
        }
    }

    function onModalClose() {
        isModalOnFocus = false;
        document.getElementById("bottomModal").innerHTML = '';

    }

    $(document).ready(function () {
        $.listen('parsley:field:validate', function () {
            validateFront();
        });

        $('button[name="confirm"]').on('click', function () {
            $('#demo-form2').parsley().validate();
            validateFront();
            if (!isModalOnFocus) {
                createModals();
            }
        });

        var validateFront = function () {
            if (true === $('#demo-form2').parsley().isValid()) {
                $('.bs-callout-warning').addClass('hidden');
                $('.bs-callout-info').removeClass('hidden');
            } else {
                $('.bs-callout-info').addClass('hidden');
                $('.bs-callout-warning').removeClass('hidden');
            }
        };
    });
    try {
        hljs.initHighlightingOnLoad();
    } catch (err) {
    }
</script>
<!-- /Parsley -->
