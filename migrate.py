import os

source_root = r"C:\Users\ASUS\Downloads\Workshop-JDBC-2526-main (1)\Workshop-JDBC-2526-main\WorkshopJDBC\src\main"
target_root = r"C:\Users\ASUS\Downloads\Workshop-JDBC-2526-main (1)\Workshop-JDBC-2526-main\TGV-JavaFX\src\main"

files_to_copy = [
    (r"java\tn\esprit\reclamation\models\Reclamation.java", r"java\com\tgv\models\Reclamation.java"),
    (r"java\tn\esprit\reclamation\models\Reponse.java", r"java\com\tgv\models\Reponse.java"),
    (r"java\tn\esprit\reclamation\services\ServiceReclamation.java", r"java\com\tgv\services\ReclamationService.java"),
    (r"java\tn\esprit\reclamation\services\ServiceReponse.java", r"java\com\tgv\services\ReponseService.java"),
    (r"java\tn\esprit\reclamation\controllers\ReclamationController.java", r"java\com\tgv\controllers\ReclamationController.java"),
    (r"java\tn\esprit\reclamation\controllers\MesReclamationsController.java", r"java\com\tgv\controllers\MesReclamationsController.java"),
    (r"java\tn\esprit\reclamation\controllers\ModifierReclamationController.java", r"java\com\tgv\controllers\ModifierReclamationController.java"),
    (r"java\tn\esprit\reclamation\controllers\AdminHomeController.java", r"java\com\tgv\controllers\AdminHomeController.java"),
    (r"java\tn\esprit\reclamation\controllers\AdminReclamationsController.java", r"java\com\tgv\controllers\AdminReclamationsController.java"),
    (r"java\tn\esprit\reclamation\controllers\AdminAjouterReponseController.java", r"java\com\tgv\controllers\AdminReponseController.java"),
    (r"resources\reclamation-view.fxml", r"resources\com\tgv\views\reclamation-view.fxml"),
    (r"resources\mes-reclamations.fxml", r"resources\com\tgv\views\mes-reclamations.fxml"),
    (r"resources\modifier-reclamation.fxml", r"resources\com\tgv\views\modifier-reclamation.fxml"),
    (r"resources\admin-home.fxml", r"resources\com\tgv\views\admin-home.fxml"),
    (r"resources\admin-reclamations.fxml", r"resources\com\tgv\views\admin-reclamations.fxml")
]

for src_rel, tgt_rel in files_to_copy:
    src = os.path.join(source_root, src_rel)
    tgt = os.path.join(target_root, tgt_rel)
    if os.path.exists(src):
        with open(src, 'r', encoding='utf-8') as f:
            content = f.read()

        # Class renames
        content = content.replace("ServiceReclamation", "ReclamationService")
        content = content.replace("ServiceReponse", "ReponseService")
        content = content.replace("AdminAjouterReponseController", "AdminReponseController")
        content = content.replace("tn.esprit.utils.MyDatabase", "com.tgv.utils.MyDatabase")

        # Package / imports replacements
        content = content.replace("tn.esprit.reclamation.controllers", "com.tgv.controllers")
        content = content.replace("tn.esprit.reclamation.models", "com.tgv.models")
        content = content.replace("tn.esprit.reclamation.services", "com.tgv.services")
        content = content.replace("tn.esprit.reclamation", "com.tgv")
        
        os.makedirs(os.path.dirname(tgt), exist_ok=True)
        with open(tgt, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Copied and updated {src_rel} -> {tgt_rel}")
    else:
        print(f"Missing {src}")
