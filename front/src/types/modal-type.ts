export type ModalProps = {
  isOpen: boolean;
  header: string;
  content: string;

  callback?: () => void;
  cancel?: boolean;
}