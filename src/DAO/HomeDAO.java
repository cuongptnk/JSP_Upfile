package DAO;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HomeDAO {
	public static void UploadSingleFile(HttpServletRequest request, HttpServletResponse response) {
		
		final String Address = "D:\\";
		final int yourMaxMemorySize = 1024 * 1024 * 3;//3MB
		final int yourMaxRequestSize = 1024 * 1024 * 50;//50MB
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			request.setAttribute("msg", "Not have enctype=multipart/form-data");
		} 
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// Set factory constraints
		factory.setSizeThreshold(yourMaxMemorySize);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Set overall request size constraint
		upload.setSizeMax(yourMaxRequestSize);

		
		try {
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			        //processFormField(item);
			    } else {
			        //processUploadedFile(item);
			    	String fileName = item.getName();
			    	//Final locaiton of upload file in server
			    	String pathFile = Address + File.separator + fileName;
			    	
			    	File uploadedFile = new File(pathFile);
			        try {
						item.write(uploadedFile);
					} catch (Exception e) {
						request.setAttribute("msg", e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			    
			    RequestDispatcher rd = request.getRequestDispatcher("View/Result.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (FileUploadException e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace();
		}

		
	}
}
