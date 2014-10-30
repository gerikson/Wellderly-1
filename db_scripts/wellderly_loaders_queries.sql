#update gene.ref_coverage set chromosome = 'chr19', patient_id = 'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;
update gene.variant_quality set patient_id =  'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;
update gene.segments_nondiploid_beta set patient_id = 'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;
update gene.segments_diploid_beta set patient_id = 'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;
update gene.depth_of_coverage set patient_id = 'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;
update gene.diploid_beta_detail set patient_id = 'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;
update gene.nondiploid_beta_detail set patient_id = 'HE00426', loaded_by = 'ETL Process', load_date = '15-Apr-2014' where patient_id is null;


