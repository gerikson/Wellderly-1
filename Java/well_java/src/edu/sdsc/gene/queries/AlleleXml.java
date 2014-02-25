package edu.sdsc.bigonc.queries;

import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import edu.sdsc.dao.PostConn;
import edu.sdsc.bigonc.queries.Allele;

@Path("/xml/")
public class AlleleXml {

	@Path("/vcf_data_xml/*")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAlleleXML(@QueryParam("af_start") Float af_start,
			@QueryParam("af_end") Float af_end) throws Exception {

		PreparedStatement statement = null;
		Connection conn = null;
		Allele alleleFreq = new Allele();
		File file = new File("/tmp/frequency_query.xml");
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter os = new OutputStreamWriter(fos);

		try {

			conn = PostConn.getConn();
			conn.setAutoCommit(false);
			String query = "Select patient_id, mutation_id, chrom, pos, ref, alt, ac, an, af from gene.vcf  where af between ? and ? order by patient_id, mutation_id, chrom, pos, ref, alt";
			statement = conn.prepareStatement(query);
			statement.setFloat(1, af_start);
			statement.setFloat(2, af_end);
			ResultSet rs = statement.executeQuery();

			javax.xml.bind.JAXBContext jaxbCtx = JAXBContext
					.newInstance(Allele.class);
			javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,
					"UTF-8"); // NOI18N
			marshaller.setProperty(
					javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			os.write("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");

			int i = 0;
			while (rs.next()) {

				alleleFreq.setPatientId(rs.getInt("patient_id"));
				alleleFreq.setAlleleId(rs.getString("mutation_id"));
				alleleFreq.setChrom(rs.getString("chrom"));
				alleleFreq.setPos(rs.getInt("pos"));
				alleleFreq.setRef(rs.getString("ref"));
				alleleFreq.setAlt(rs.getString("alt"));
				alleleFreq.setAf(rs.getFloat("af"));
				alleleFreq.setAn(rs.getInt("an"));
				alleleFreq.setAc(rs.getInt("ac"));
				os.write("\n");
				marshaller.marshal(alleleFreq, os);
				i++;

			}

			System.out.println("Record count:" + i + "\n");

		} catch (Exception e) {
			System.out.println(e.toString());

		} finally {
			if (conn != null)
				conn.close();

		}
		return Response
				.ok(file)
				.header("Content-Disposition",
						"attachment; filename=" + file.getName()).build();
	}

}
