
import toast from 'react-hot-toast';


export default function ConfirmDeleteToast({t, onConfirm}) {
  return (
    <div className="bg-gray-400 p-4 rounded-xl shadow-xl text-white border border-black font-bold">
      <p>Are you sure you want to delete?</p>

      <div className="flex justify-center gap-2 mt-5">
        <button
          onClick={() => toast.remove(t.id)}
          className="px-3 py-1 rounded bg-gray-600 hover:scale-105 hover:cursor-pointer hover:bg-gray-200"
        >
          Cancel
        </button>

        <button
          onClick={() => {
            toast.remove(t.id);
            onConfirm();
          }}
          className="px-3 py-1 rounded bg-red-600 text-white hover:bg-red-400 hover:cursor-pointer hover:border-white border-black hover:scale-105"
        >
          Confirm
        </button>
      </div>
    </div>
  )
}
