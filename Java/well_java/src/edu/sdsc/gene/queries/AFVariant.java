package edu.sdsc.gene.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import edu.sdsc.dao.WellConn;

import java.util.*;

@Path("/variant")
public class AFVariant {

	Float LAST_FREQ;
	String LAST_KEY;

	@Path("/sg_data")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public HashMap<String, String> getVariantData(@QueryParam("allele_freq") float allele_freq,  @QueryParam("offset") Integer offset)
			throws Exception {

		//String returnResult = "css th#short{width:10;} td#short {width:10;};";
		Connection conn = null;
		//double allele_freq = 0.01;

		try {

			conn = WellConn.getConn();
			//conn.setAutoCommit(false);
			String query = "Select patient_id, go_id, gene,  chromosome, gene, allele, reference, begin_pos, end_pos, genomes_af, wellderly_af325 "
					+ "from gene.sg_advisor "
					+ "where wellderly_af325 <= ? "
					+ "order by chromosome, begin_pos "
					+ "limit 100 offset ?";
					//+ "and v.genome_af <= ?" + " and v.nhlbi <= ?";*/

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDouble(1, allele_freq);
			ps.setInt(2, offset);
			ResultSet rs = ps.executeQuery();
			/*returnResult = "<html><body><title>BigOnc Researcher Toolbox</title>";
			returnResult += "<link rel=\"stylesheet\" " +
					"href=\"http://bigonc-stsi.sdsc.edu:8080/bigonc/custom.css\"" +
					"media=\"screen, projection\" />" +
					"<h1>For Mutations with Allele Frequencies Less Than or Equal to "
					+ allele_freq  + "</h1><br><table align=\"center\" id=\"direction\"><tr><td><a href=\"?af_start=" + allele_freq + "&offset=" + (offset - 100) +"\">Previous 100</a></td><td><a href=\"http://bigonc-stsi.sdsc.edu:8080/bigonc/allele_query.html\">New Query</a></td><td><a href=\"?allele_freq=" + allele_freq + "&offset=" + (offset + 100) +"\">Next 100</a></td></tr></table>";
			returnResult += "<table id=\"records\"><tr><th>Patient ID</th><th>Chromosome</th><th>GO ID</th><th>Gene Name</th><th>Alternate</th><th>Reference</th><th>Beginning Position</th><th>End Position</th><th>Genome AF</th><th>Wellderly AF</th><tr>";*/
			
			while (rs.next()) {

				/*returnResult += "<tr><td>" + rs.getString("patient_id")
						+ "</td><td>" + rs.getString("chromosome") + "</td><td>"
						+ rs.getString("go_id") + "</td><td>"
						+ rs.getString("gene") + "</td><td id=\"short\">"
						+ rs.getString("allele") + "</td><td id=\"short\">"
						+ rs.getString("reference") + "</td><td>"
						+ rs.getString("begin_pos") + "</td><td>"
						+ rs.getString("end_pos") + "</td><td id=\"short\">"
						+ rs.getString("genomes_af")
						+ "</td><td id=\"short\">"
						+ rs.getString("wellderly_af325") + "</td></tr>";*/
				HashMap<String, String> returnResult = new HashMap;
				
				

			}
			//returnResult += "</table></body</html>";

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
