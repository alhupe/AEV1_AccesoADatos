package ae;

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
    private String directorioSeleccionado;
    private JTextField txtBuscarPalabras;

    /**
     * Método principal que inicia la aplicación.
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
     * Constructor de la clase App que inicializa la interfaz de usuario.
     */
    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 554, 379);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5);
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

        JButton btnFusionar = new JButton("Fusionar");

        // Acción del botón Fusionar
        btnFusionar.addActionListener(new ActionListener() {
            /**
             * Acción que se realiza al hacer clic en el botón "Fusionar".
             *
             * @param {ActionEvent} e - El evento de clic.
             */
            public void actionPerformed(ActionEvent e) {
                // Crear un JFileChooser para seleccionar los dos archivos a fusionar
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();

                    if (selectedFiles.length != 2) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar exactamente dos archivos para fusionar.");
                    } else {
                        // Crear un JFileChooser para seleccionar el directorio de destino y el nombre del archivo fusionado
                        JFileChooser saveFileChooser = new JFileChooser();
                        saveFileChooser.setDialogTitle("Guardar archivo fusionado");
                        result = saveFileChooser.showSaveDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            File destinationFile = saveFileChooser.getSelectedFile();

                            if (destinationFile.exists()) {
                                int response = JOptionPane.showConfirmDialog(null,
                                        "El archivo ya existe. ¿Desea sobrescribirlo?", "Confirmar sobrescritura",
                                        JOptionPane.YES_NO_OPTION);
                                if (response != JOptionPane.YES_OPTION) {
                                    return;
                                }
                            }

                            try (FileWriter writer = new FileWriter(destinationFile)) {
                                // Fusionar los contenidos de los dos archivos
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

        txtOrdenar = new JTextField();
        txtOrdenar.setEditable(false);
        txtOrdenar.setText("Ordenar:");
        txtOrdenar.setBounds(402, 82, 86, 20);
        contentPane.add(txtOrdenar);
        txtOrdenar.setColumns(10);

        JButton btnExplorar = new JButton("Explorar");

        // Acción del botón Explorar
        btnExplorar.addActionListener(new ActionListener() {
            /**
             * Acción que se realiza al hacer clic en el botón "Explorar".
             *
             * @param {ActionEvent} e - El evento de clic.
             */
            public void actionPerformed(ActionEvent e) {
                JFileChooser buscadorArchivos = new JFileChooser();
                // Configurar el JFileChooser para que solo muestre directorios
                buscadorArchivos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                Component frame = null;
                // Mostrar el cuadro de diálogo del explorador de archivos
                int resultado = buscadorArchivos.showDialog(frame, "Seleccionar Directorio");

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    // El usuario seleccionó un directorio
                    directorioSeleccionado = buscadorArchivos.getSelectedFile().getAbsolutePath();
                    // Puedes hacer algo con la ruta del directorio seleccionado
                    System.out.println("Directorio seleccionado: " + directorioSeleccionado);
                    txtFitxer.setText(directorioSeleccionado);
                    File directorio = new File(directorioSeleccionado);
                    File[] archivos = directorio.listFiles();

                    // Limpiar el JTextArea antes de mostrar la nueva información
                    txtInfo.setText("");

                    if (archivos != null) {
                        boolean seEncontraronArchivos = false; // Variable para registrar si se encontraron archivos .txt

                        for (File archivo : archivos) {
                            // Verificar si el archivo tiene la extensión .txt
                            if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".txt")) {
                                seEncontraronArchivos = true;
                                System.out.println("Ha encontrado 1 archivo");
                                String nombreArchivo = archivo.getName();
                                long tamanoArchivo = archivo.length();
                                long fechaModificacion = archivo.lastModified();

                                // Formatear la fecha de modificación como una cadena legible
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                String fechaModificacionStr = dateFormat.format(new Date(fechaModificacion));

                                // Agregar la información al JTextArea
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

        txtBuscarPalabras = new JTextField();
        txtBuscarPalabras.setText("Buscar contenido archivos...");
        txtBuscarPalabras.setBounds(10, 22, 236, 20);
        contentPane.add(txtBuscarPalabras);
        txtBuscarPalabras.setColumns(10);

        JButton btnBuscarPalabra = new JButton("Buscar Palabra");

        // Acción del botón Buscar Palabra
        btnBuscarPalabra.addActionListener(new ActionListener() {
            /**
             * Acción que se realiza al hacer clic en el botón "Buscar Palabra".
             *
             * @param {ActionEvent} e - El evento de clic.
             */
            public void actionPerformed(ActionEvent e) {
                String palabra = txtBuscarPalabras.getText();

                if (palabra.isEmpty()) {
                    txtInfo.setText("No has escrito ninguna palabra");
                } else {
                    String directorioPath = txtFitxer.getText();
                    File directorioSeleccionado = new File(directorioPath);

                    if (directorioSeleccionado.isDirectory()) {
                        File[] archivos = directorioSeleccionado.listFiles(new FilenameFilter() {
                            public boolean accept(File dir, String name) {
                                return name.toLowerCase().endsWith(".txt");
                            }
                        });

                        // Limpiar el JTextArea antes de mostrar la nueva información
                        txtInfo.setText("");

                        if (archivos != null && archivos.length > 0) {
                            for (File archivo : archivos) {
                                try {
                                    BufferedReader br = new BufferedReader(new FileReader(archivo));
                                    String linea;
                                    int coincidencias = 0;

                                    while ((linea = br.readLine()) != null) {
                                        // Buscar la palabra en cada línea del archivo
                                        coincidencias += contarCoincidencias(linea, palabra);
                                    }

                                    br.close();

                                    String nombreArchivo = archivo.getName();
                                    // Agregar la información al JTextArea
                                    txtInfo.append("Coincidencias" + "' en el archivo " + nombreArchivo + ": " + coincidencias + "\n\n");
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

        JButton btnOrdenar = new JButton("Ordenar");

        // Acción del botón Ordenar
        btnOrdenar.addActionListener(new ActionListener() {
            /**
             * Acción que se realiza al hacer clic en el botón "Ordenar".
             *
             * @param {ActionEvent} e - El evento de clic.
             */
            public void actionPerformed(ActionEvent e) {
                String palabra = txtBuscarPalabras.getText();

                if (palabra.isEmpty()) {
                    txtInfo.setText("No has escrito ninguna palabra");
                } else {
                    // Obtener la lista de archivos .txt en el directorio
                    File directorioSeleccionado = new File(txtFitxer.getText());
                    File[] archivos = directorioSeleccionado.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".txt");
                        }
                    });

                    // Limpiar el JTextArea antes de mostrar la nueva información
                    txtInfo.setText("");

                    if (archivos != null && archivos.length > 0) {
                        // Ordenar los archivos según la selección del usuario
                        if (rdbAlfabetic.isSelected()) {
                            Arrays.sort(archivos, (a, b) -> a.getName().compareTo(b.getName()));
                        } else if (rdbTamany.isSelected()) {
                            Arrays.sort(archivos, (a, b) -> Long.compare(a.length(), b.length()));
                        } else if (rdbUltimaMod.isSelected()) {
                            Arrays.sort(archivos, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
                        }

                        // Aplicar el orden ascendente o descendente
                        if (cmbTipus.getSelectedIndex() == 1) {
                            // Si es descendente, invertir el arreglo
                            List<File> archivoList = Arrays.asList(archivos);
                            Collections.reverse(archivoList);
                            archivos = archivoList.toArray(new File[0]);
                        }

                        for (File archivo : archivos) {
                            String nombreArchivo = archivo.getName();
                            long tamanoArchivo = archivo.length();
                            long fechaModificacion = archivo.lastModified();

                            // Formatear la fecha de modificación como una cadena legible
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
     * Método para contar las coincidencias de una palabra en una línea.
     *
     * @param {string} linea - La línea en la que buscar coincidencias.
     * @param {string} palabra - La palabra a buscar.
     * @returns {number} El número de coincidencias encontradas.
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
