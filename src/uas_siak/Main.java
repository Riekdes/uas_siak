/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rieke
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setDatabaseName("uas?serverTimezone=UTC");
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);

        try {
            Connection conn = dataSource.getConnection();
            Scanner input = new Scanner(System.in);

            Boolean mainmenu = true;
            while (mainmenu) {
                System.out.println("\n1. Nilai Mahasiswa");
                System.out.println("2. Data Mahasiswa");
                System.out.println("3. Data Mata Kuliah");
                System.out.println("0. Keluar");
                System.out.print("\nPilih : ");
                String pilihanutama = input.nextLine();
                if (Integer.parseInt(pilihanutama) == 1) {
                    Boolean menu_mhs = true;
                    while (menu_mhs) {
                        System.out.println("\n1. Lihat Nilai");
                        System.out.println("2. Input Nilai");
                        System.out.println("0. Kembali");
                        System.out.print("\nPilih : ");
                        String pilih_mhs = input.nextLine();
                        if (Integer.parseInt(pilih_mhs) == 1) {
                            PreparedStatement lihat = conn.prepareStatement("SELECT * FROM nilai JOIN mahasiswa ON nilai.nim = mahasiswa.nim JOIN matakuliah ON nilai.kode_mk = matakuliah.kode_mk ORDER BY nilai.nim ASC");
                            ResultSet allmhs = lihat.executeQuery();
                            System.out.println("\nNIM \t | Nama Mahasiswa \t | Mata Kuliah \t\t\t | Nilai");
                            System.out.println("-----------------------------------------------------------------------------------------------");
                            while(allmhs.next()) {
                                System.out.println(allmhs.getInt("nilai.nim") + "\t | " + allmhs.getString("mahasiswa.nama") + " \t | " + allmhs.getString("matakuliah.nama_mk") + " \t\t | " + allmhs.getString("nilai"));
                            }
                        }
                        if (Integer.parseInt(pilih_mhs) == 2) {
                            System.out.print("NIM : ");
                            String nim = input.nextLine();
                            PreparedStatement cariMhsStatement = conn.prepareStatement("SELECT * FROM mahasiswa WHERE nim = ?");
                            cariMhsStatement.setInt(1, Integer.parseInt(nim));
                            ResultSet mhs = cariMhsStatement.executeQuery();
                            if (mhs.next()) {
                                System.out.print("\nKode Matkul : ");
                                String kdmk = input.nextLine();
                                PreparedStatement cariMkStatement = conn.prepareStatement("SELECT * FROM mahasiswa WHERE nim = ?");
                                cariMkStatement.setInt(1, Integer.parseInt(nim));
                                ResultSet mk = cariMkStatement.executeQuery();
                                if (mk.next()) {
                                    System.out.println("\nKode Matkul : ");
                                    String nilai = input.nextLine();
                                    PreparedStatement insert = conn.prepareStatement("INSERT INTO nilai VALUES(?,?,?)");
                                    insert.setInt(1, Integer.parseInt(nim));
                                    insert.setInt(2, Integer.parseInt(kdmk));
                                    if (Integer.parseInt(nilai) > 80) {
                                        insert.setString(3, "A");
                                    } else if (Integer.parseInt(nilai) >= 70) {
                                        insert.setString(3, "B");
                                    } else if (Integer.parseInt(nilai) >= 60) {
                                        insert.setString(3, "C");
                                    } else if (Integer.parseInt(nilai) >= 50) {
                                        insert.setString(3, "D");
                                    } else {
                                        insert.setString(3, "E");
                                    }
                                    insert.executeUpdate();
                                } else {
                                    System.out.println("Mata Kuliah dengan Kode "+kdmk+" tidak ada");
                                }
                            } else {
                                System.out.println("Mahasiswa dengan NIM "+nim+" tidak ada");
                            }
                        }
                        if (Integer.parseInt(pilih_mhs) == 0) {
                            menu_mhs = false;
                        }
                    }
                } if (Integer.parseInt(pilihanutama) == 2) {
                    Boolean menu_mhs = true;
                    while (menu_mhs) {
                        System.out.println("\n1. Lihat Mahasiswa");
                        System.out.println("2. Input Mahasiswa");
                        System.out.println("0. Kembali");
                        System.out.print("\nPilih : ");
                        String pilih_mhs = input.nextLine();
                        if (Integer.parseInt(pilih_mhs) == 1) {
                            PreparedStatement lihat = conn.prepareStatement("SELECT * FROM mahasiswa");
                            ResultSet allmhs = lihat.executeQuery();
                            System.out.println("\nNIM \t | Nama Mahasiswa \t | Tanggal Lahir");
                            System.out.println("==================================================================");
                            while (allmhs.next()) {
                                System.out.println(allmhs.getInt("nim") + "\t | " + allmhs.getString("nama") + " \t | " + allmhs.getString("tgl_lahir"));
                            }
                        }
                        if (Integer.parseInt(pilih_mhs) == 2) {
                            System.out.print("NIM : ");
                            String nim = input.nextLine();
                            System.out.print("Nama : ");
                            String nama = input.nextLine();
                            System.out.print("Tanggal Lahir : ");
                            String tgl_lahir = input.nextLine();

                            PreparedStatement insert = conn.prepareStatement("INSERT INTO mahasiswa VALUES(?,?,?)");
                            insert.setInt(1, Integer.parseInt(nim));
                            insert.setString(2, nama);
                            insert.setString(3, tgl_lahir);
                            insert.executeUpdate();
                        }
                        if (Integer.parseInt(pilih_mhs) == 0) {
                            menu_mhs = false;
                        }
                    }
                } if (Integer.parseInt(pilihanutama) == 3) {
                    Boolean menu_mk = true;
                    while (menu_mk) {
                        System.out.println("\n1. Lihat Mata Kuliah");
                        System.out.println("2. Input Mata Kuliah");
                        System.out.println("0. Kembali");
                        System.out.print("\nPilih : ");
                        String pilih_mhs = input.nextLine();
                        if (Integer.parseInt(pilih_mhs) == 1) {
                            PreparedStatement lihat = conn.prepareStatement("SELECT * FROM matakuliah");
                            ResultSet allmk = lihat.executeQuery();
                            System.out.println("\nKode \t | Mata Kuliah \t\t\t | SKS");
                            System.out.println("==============================================================");
                            while (allmk.next()) {
                                System.out.println(allmk.getInt("kode_mk") + "\t | " + allmk.getString("nama_mk") + " \t\t | " + allmk.getInt("sks"));
                            }
                        }
                        if (Integer.parseInt(pilih_mhs) == 2) {
                            System.out.print("Nama Mata Kuliah : ");
                            String nama = input.nextLine();
                            System.out.print("SKS : ");
                            String sks = input.nextLine();

                            PreparedStatement insert = conn.prepareStatement("INSERT INTO matakuliah(nama_mk,sks) VALUES(?,?)");
                            insert.setString(1, nama);
                            insert.setInt(2, Integer.parseInt(sks));
                            insert.executeUpdate();
                        }
                        if (Integer.parseInt(pilih_mhs) == 0) {
                            menu_mk = false;
                        }
                    }
                } if (Integer.parseInt(pilihanutama) == 0) {
                    mainmenu = false;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
