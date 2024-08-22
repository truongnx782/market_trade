package market.demo.Util;

import org.apache.poi.ss.usermodel.Cell;

public  class Excel {
    public static String getCellValue(Cell cell) {
        if (cell == null) return "Empty";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return (cell.getNumericCellValue() % 1 == 0)
                        ? String.format("%.0f", cell.getNumericCellValue())
                        : String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "Unsupported cell type";
        }
    }
}
