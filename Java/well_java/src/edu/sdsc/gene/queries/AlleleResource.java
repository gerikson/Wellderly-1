package edu.sdsc.bigonc.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import edu.sdsc.dao.PostConn;

@Path("/gene/")
public class AlleleResource {

	Float LAST_FREQ;
	String LAST_KEY;
	

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAlleleFrequency() {
		return "<p>BigOnc Query Pages</p>";
	}

	@Path("/vcf_data/*")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAlleleData(@QueryParam("af_start") Float af_start, @QueryParam("af_end") Float af_end, @QueryParam("offset") Integer offset) throws Exception {

		String returnResult = "css th#short{width:10;} td#short {width:10;};";
		Connection conn = null;
		
		if(af_start > af_end) {
			//if the user send a smaller value for the end value reverse the order so that it always returns a dataset
			Float chkAfStart, chkAfEnd;
			chkAfStart = af_end;
			chkAfEnd = af_start;
			af_start = chkAfStart;
			af_end = chkAfEnd;
			
		}	
		
		try {

			if (offset < 0) offset = 0;
			conn = PostConn.getConn();
			conn.setAutoCommit(false);
			String query = "Select patient_id, mutation_id, chrom, pos, ref, alt, ac, an, af from gene.vcf  where af between ? and ? order by patient_id, mutation_id, chrom, pos, ref, alt limit 100 offset ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setFloat(1, af_start);
			ps.setFloat(2, af_end);
			ps.setInt(3, offset);
			ResultSet rs = ps.executeQuery();
			returnResult = "<html><body><title>Query Mutations by Allele Frequency</title>";
			returnResult += "<link rel=\"stylesheet\" " +
					"href=\"http://bigonc-stsi.sdsc.edu:8080/bigonc/custom.css\"" +
					"media=\"screen, projection\" />" +
					"<h1>For Mutations with Allele Frequencies Between "
					+ af_start + " and " + af_end +  "</h1><br><table align=\"center\" id=\"direction\"><tr><td><a href=\"?af_start=" + af_start + "&af_end="+ af_end + "&offset=" + (offset - 100) +"\">Previous 100</a></td><td><a href=\"http://bigonc-stsi.sdsc.edu:8080/bigonc/allele_query.html\">New Query</a></td><td><a href=\"?af_start=" + af_start + "&af_end="+ af_end + "&offset=" + (offset + 100) +"\">Next 100</a></td></tr></table>";
			returnResult += "<table id=\"records\"><tr><th>Patient ID</th><th>Allele ID</th><th>Chromosome</th><th>Position</th><th>Reference</th><th>Alternate</th><th>Allele Count</th><th>Total Allele Count</th><th>Allele Frequency</th><tr>";
			
			while (rs.next()) {
				
				returnResult += "<tr><td>" + rs.getString("patient_id")
						+ "</td><td>" + rs.getString("mutation_id")
						+ "</td><td>" + rs.getString("chrom")
						+ "</td><td>" + rs.getString("pos")
						+ "</td><td id=\"short\">" + rs.getString("ref")
						+ "</td><td id=\"short\">" + rs.getString("alt")
						+ "</td><td id=\"short\">" + rs.getString("ac")
						+ "</td><td id=\"short\">" + rs.getString("an")
						+ "</td><td id=\"short\">" + rs.getString("af")
						+ "</td></tr>";

			}
			returnResult += "</table></body</html>";

		} catch (Exception e) {
			returnResult = e.toString();
			return returnResult;
		} finally {
			if (conn != null)
				conn.close();

		}
		return returnResult;
	}
}