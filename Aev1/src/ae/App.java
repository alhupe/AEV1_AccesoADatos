package ae;

import java.awt.Component;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField txtOrdenar;
	private String directorioActual;
	private JTextField txtBuscarPalabras;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 72, 348, 246);
		contentPane.add(scrollPane_1);

		JTextArea txtInfo = new JTextArea();
		scrollPane_1.setViewportView(txtInfo);

		JRadioButton rdbAlfabetic = new JRadioButton("Alfabetic");
		buttonGroup_1.add(rdbAlfabetic);
		rdbAlfabetic.setBounds(402, 122, 109, 23);
		contentPane.add(rdbAlfabetic);

		JRadioButton rdbTamany = new JRadioButton("Tamany");
		buttonGroup_1.add(rdbTamany);
		rdbTamany.setBounds(402, 148, 109, 23);
		contentPane.add(rdbTamany);

		JRadioButton rdbUltimaMod = new JRadioButton("Ultima modificacio");
		buttonGroup_1.add(rdbUltimaMod);
		rdbUltimaMod.setBounds(402, 174, 109, 23);
		contentPane.add(rdbUltimaMod);

		JComboBox cmbTipus = new JComboBox();
		cmbTipus.setModel(new DefaultComboBoxModel(new String[] { "Ascendent", "Descendent" }));
		cmbTipus.setBounds(402, 206, 109, 23);
		contentPane.add(cmbTipus);

		/**
		 * Crea y configura un botón que permite fusionar varios archivos seleccionados en uno solo. Además, proporciona la funcionalidad para guardar el archivo fusionado en el directorio especificado.
		 */
		JButton btnFusionar = new JButton("Fusionar");
		btnFusionar.addActionListener(new ActionListener() {
		    /**
		     * Acción realizada al hacer clic en el botón "Fusionar". Permite al usuario seleccionar varios archivos para fusionar, así como especificar la ubicación y el nombre del archivo fusionado resultante.
		     * @param e El evento de acción que desencadena el método.
		     */
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setCurrentDirectory(new File(directorioActual));

		        fileChooser.setMultiSelectionEnabled(true);

		        // Filtro de archivo para mostrar solo archivos .txt
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
		        fileChooser.setFileFilter(filter);

		        int result = fileChooser.showOpenDialog(null);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            File[] selectedFiles = fileChooser.getSelectedFiles();

		            if (selectedFiles.length < 2) {
		                JOptionPane.showMessageDialog(null, "Debe seleccionar al menos dos archivos para fusionar.");
		            } else {
		                JFileChooser saveFileChooser = new JFileChooser();
		                saveFileChooser.setCurrentDirectory(new File(directorioActual));

		                saveFileChooser.setDialogTitle("Guardar archivo fusionado");
		                result = saveFileChooser.showSaveDialog(null);

		                if (result == JFileChooser.APPROVE_OPTION) {
		                    File destinationFile = saveFileChooser.getSelectedFile();
		                    String filePath = destinationFile.getAbsolutePath();

		                    if (!filePath.toLowerCase().endsWith(".txt")) {
		                        destinationFile = new File(filePath + ".txt");
		                    }

		                    if (destinationFile.exists()) {
		                        int response = JOptionPane.showConfirmDialog(null,
		                                "El archivo ya existe. ¿Desea sobrescribirlo?", "Confirmar sobrescritura",
		                                JOptionPane.YES_NO_OPTION);
		                        if (response != JOptionPane.YES_OPTION) {
		                            return;
		                        }
		                    }

		                    try (FileWriter writer = new FileWriter(destinationFile)) {
		                        for (File sourceFile : selectedFiles) {
		                            try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
		                                String line;
		                                while ((line = reader.readLine()) != null) {
		                                    writer.write(line);
		                                    writer.write(System.lineSeparator());
		                                }
		                            }
		                        }
		                        writer.flush();
		                        JOptionPane.showMessageDialog(null, "Archivos fusionados exitosamente.");
		                    } catch (IOException ex) {
		                        ex.printStackTrace();
		                        JOptionPane.showMessageDialog(null, "Error al fusionar archivos.");
		                    }
		                }
		            }
		        }
		    }
		});
		btnFusionar.setBounds(399, 300, 102, 29);
		contentPane.add(btnFusionar);



		/**
		 * Campo de texto que indica la acción "Ordenar" y muestra el estado actual del proceso de ordenación.
		 */
		txtOrdenar = new JTextField();
		txtOrdenar.setEditable(false);
		txtOrdenar.setText("Ordenar:");
		txtOrdenar.setBounds(402, 82, 86, 20);
		contentPane.add(txtOrdenar);
		txtOrdenar.setColumns(10);


		/**
		 * Crea y configura un botón que permite al usuario explorar y seleccionar un directorio. Muestra información detallada sobre los archivos encontrados en el directorio seleccionado que cumplen con ciertos criterios de filtrado. También maneja la funcionalidad de búsqueda de contenido en archivos.
		 */
		JButton btnExplorar = new JButton("Explorar");
		btnExplorar.addActionListener(new ActionListener() {
		    /**
		     * Acción realizada al hacer clic en el botón "Explorar". Permite al usuario seleccionar un directorio y muestra información detallada sobre los archivos encontrados que cumplen con ciertos criterios de filtrado.
		     * @param e El evento de acción que desencadena el método.
		     */
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser buscadorArchivos = new JFileChooser();
		        buscadorArchivos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        Component frame = null;

		        int resultado = buscadorArchivos.showDialog(frame, "Seleccionar Directorio");

		        if (resultado == JFileChooser.APPROVE_OPTION) {

		            directorioActual = buscadorArchivos.getSelectedFile().getAbsolutePath();

		            File directorio = new File(directorioActual);
		            File[] archivos = directorio.listFiles();

		            txtInfo.setText("");

		            if (archivos != null) {
		                boolean seEncontraronArchivos = false;

		                for (File archivo : archivos) {
		                    if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".txt")) {
		                        seEncontraronArchivos = true;
		                        String nombreArchivo = archivo.getName();
		                        long tamanoArchivo = archivo.length();
		                        long fechaModificacion = archivo.lastModified();

		                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		                        String fechaModificacionStr = dateFormat.format(new Date(fechaModificacion));

		                        txtInfo.append("Nombre: " + nombreArchivo + "\n");
		                        txtInfo.append("Tamaño: " + tamanoArchivo + " bytes\n");
		                        txtInfo.append("Fecha de modificación: " + fechaModificacionStr + "\n\n");
		                    }
		                }

		                if (!seEncontraronArchivos) {
		                    txtInfo.setText("No hi ha fitxers txt en el directori");
		                }
		            }
		        }
		    }
		});

		btnExplorar.setBounds(402, 21, 89, 23);
		contentPane.add(btnExplorar);

		/**
		 * Componente de campo de texto que permite al usuario escribir y buscar contenido específico en los archivos del directorio seleccionado.
		 */
		txtBuscarPalabras = new JTextField();
		txtBuscarPalabras.setText("Buscar contenido archivos...");
		txtBuscarPalabras.setBounds(10, 22, 236, 20);
		contentPane.add(txtBuscarPalabras);
		txtBuscarPalabras.setColumns(10);

		txtBuscarPalabras.addFocusListener(new FocusListener() {
		    /**
		     * Método que se llama cuando el campo de texto gana el foco. Borra el texto predeterminado si el campo de texto está en su estado predeterminado.
		     * @param e El evento de enfoque que desencadena el método.
		     */
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (txtBuscarPalabras.getText().equals("Buscar contenido archivos...")) {
		            txtBuscarPalabras.setText("");
		        }
		    }

		    /**
		     * Método que se llama cuando el campo de texto pierde el foco. Restaura el texto predeterminado si el campo de texto está vacío.
		     * @param e El evento de enfoque que desencadena el método.
		     */
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (txtBuscarPalabras.getText().isEmpty()) {
		            txtBuscarPalabras.setText("Buscar contenido archivos...");
		        }
		    }
		});


		/**
		 * Crea y configura un botón que busca una palabra específica en los archivos del directorio actual y muestra las coincidencias encontradas.
		 */
		JButton btnBuscarPalabra = new JButton("Buscar Palabra");
		btnBuscarPalabra.addActionListener(new ActionListener() {
		    /**
		     * Realiza la búsqueda de una palabra específica en los archivos del directorio actual y muestra las coincidencias encontradas.
		     * @param e El evento de acción que desencadena el método.
		     */
		    public void actionPerformed(ActionEvent e) {
		        String palabra = txtBuscarPalabras.getText();

		        if (palabra.isEmpty()) {
		            txtInfo.setText("No has escrito ninguna palabra");
		        } else {
		            String directorioPath = directorioActual;
		            File directorioSeleccionado = new File(directorioPath);

		            if (directorioSeleccionado.isDirectory()) {
		                File[] archivos = directorioSeleccionado.listFiles(new FilenameFilter() {
		                    /**
		                     * Verifica si el archivo cumple con ciertos criterios de filtrado.
		                     * @param dir El directorio en el que se encuentra el archivo.
		                     * @param name El nombre del archivo.
		                     * @return true si el archivo cumple con los criterios de filtrado, de lo contrario false.
		                     */
		                    public boolean accept(File dir, String name) {
		                        return name.toLowerCase().endsWith(".txt");
		                    }
		                });

		                txtInfo.setText("");

		                if (archivos != null && archivos.length > 0) {
		                    for (File archivo : archivos) {
		                        try {
		                            BufferedReader br = new BufferedReader(new FileReader(archivo));
		                            String linea;
		                            int coincidencias = 0;

		                            while ((linea = br.readLine()) != null) {
		                                coincidencias += contarCoincidencias(linea, palabra);
		                            }

		                            br.close();

		                            String nombreArchivo = archivo.getName();

		                            txtInfo.append("Coincidencias" + " en el archivo '" + nombreArchivo + "': "
		                                    + coincidencias + "\n\n");
		                        } catch (IOException ex) {
		                            ex.printStackTrace();
		                        }
		                    }
		                } else {
		                    txtInfo.setText("No se encontraron archivos .txt en el directorio.");
		                }
		            } else {
		                txtInfo.setText("El directorio especificado no existe.");
		            }
		        }
		    }
		});

		btnBuscarPalabra.setBounds(256, 21, 102, 23);
		contentPane.add(btnBuscarPalabra);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(222, 174, 2, 2);
		contentPane.add(scrollPane);
		
		/**
		 * Crea y configura un botón que ordena y muestra la información de los archivos en el directorio actual según el criterio seleccionado.
		 */
		JButton btnOrdenar = new JButton("Ordenar");
		btnOrdenar.addActionListener(new ActionListener() {
			/**
			 * Ordena y muestra la información de los archivos en el directorio actual según el criterio seleccionado.
			 * @param e El evento de acción que desencadena el método.
			 */
			public void actionPerformed(ActionEvent e) {
				String palabra = txtBuscarPalabras.getText();

				if (palabra.isEmpty()) {
					txtInfo.setText("No has escrito ninguna palabra");
				} else {
					File directorioSeleccionado = new File(directorioActual);
					File[] archivos = directorioSeleccionado.listFiles(new FilenameFilter() {
						/**
						 * Verifica si el archivo cumple con ciertos criterios de filtrado.
						 * @param dir El directorio en el que se encuentra el archivo.
						 * @param name El nombre del archivo.
						 * @return true si el archivo cumple con los criterios de filtrado, de lo contrario false.
						 */
						public boolean accept(File dir, String name) {
							return name.toLowerCase().endsWith(".txt");
						}
					});

					txtInfo.setText("");

					if (archivos != null && archivos.length > 0) {
						if (rdbAlfabetic.isSelected()) {
							Arrays.sort(archivos, (a, b) -> a.getName().compareTo(b.getName()));
						} else if (rdbTamany.isSelected()) {
							Arrays.sort(archivos, (a, b) -> Long.compare(a.length(), b.length()));
						} else if (rdbUltimaMod.isSelected()) {
							Arrays.sort(archivos, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
						}

						if (cmbTipus.getSelectedIndex() == 1) {
							List<File> archivoList = Arrays.asList(archivos);
							Collections.reverse(archivoList);
							archivos = archivoList.toArray(new File[0]);
						}

						for (File archivo : archivos) {
							String nombreArchivo = archivo.getName();
							long tamanoArchivo = archivo.length();
							long fechaModificacion = archivo.lastModified();

							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							String fechaModificacionStr = dateFormat.format(new Date(fechaModificacion));

							txtInfo.append("Nombre: " + nombreArchivo + "\n");
							txtInfo.append("Tamaño: " + tamanoArchivo + " bytes\n");
							txtInfo.append("Fecha de modificación: " + fechaModificacionStr + "\n\n");
						}
					} else {
						txtInfo.setText("No se encontraron archivos .txt en el directorio.");
					}
				}
			}
		});

		btnOrdenar.setBounds(412, 240, 89, 23);
		contentPane.add(btnOrdenar);
	}

	/**
     * Cuenta el número de ocurrencias de una palabra en una línea de texto.
     * @param linea La línea de texto en la que se busca la palabra.
     * @param palabra La palabra que se busca en la línea de texto.
     * @return El número de ocurrencias de la palabra en la línea.
     */
	private int contarCoincidencias(String linea, String palabra) {
		int coincidencias = 0;
		int index = linea.indexOf(palabra);
		while (index != -1) {
			coincidencias++;
			index = linea.indexOf(palabra, index + 1);

		}
		return coincidencias;
	}
	
}